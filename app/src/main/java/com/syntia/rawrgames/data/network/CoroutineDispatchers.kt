package com.syntia.rawrgames.data.network

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

open class CoroutineDispatchers @Inject constructor() {

  open val Main: CoroutineContext by lazy { Dispatchers.Main }
  open val IO: CoroutineContext by lazy { Dispatchers.IO }
  open val Default: CoroutineContext by lazy { Dispatchers.Default }
}