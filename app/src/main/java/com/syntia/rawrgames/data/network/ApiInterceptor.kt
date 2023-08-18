package com.syntia.rawrgames.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ApiInterceptor : Interceptor {

  companion object {
    private const val TAG = "ApiInterceptor"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val requestBuilder = request.newBuilder()

    val requestUrlBuilder = request.url.newBuilder().apply {
      addQueryParameter("key", ApiConstant.RAWR_API_KEY)
    }
    requestBuilder.url(requestUrlBuilder.build())

    Log.d(TAG, "request url: $requestUrlBuilder")

    val response = chain.proceed(requestBuilder.build())
    val responseBodyString = response.body?.string().orEmpty()
    logApiResponseEvent(requestUrlBuilder.toString(), response, responseBodyString)

    val newResponseBody = responseBodyString.toResponseBody(response.body?.contentType())
    return response.newBuilder().body(newResponseBody).build()
  }

  private fun logApiResponseEvent(url: String, response: Response, responseBody: String) {
    if (response.isSuccessful) {
      Log.d(TAG, "API $url success with response: $responseBody")
    } else {
      Log.d(
        TAG, "API $url failed with response code: ${response.code} and message: ${response.message}"
      )
    }
  }
}