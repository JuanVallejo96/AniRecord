package com.example.anirecord.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(
    val Main: CoroutineDispatcher = Dispatchers.Main,
    val IO: CoroutineDispatcher = Dispatchers.IO,
)