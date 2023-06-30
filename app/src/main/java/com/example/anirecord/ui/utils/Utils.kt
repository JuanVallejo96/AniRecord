package com.example.anirecord.ui.utils

import com.example.anirecord.graphql.type.MediaSeason
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class Utils {
    companion object {
        fun getCurrentMediaSeason(): MediaSeason {
            return when (Calendar.getInstance().get(Calendar.MONTH)) {
                in Calendar.MARCH..Calendar.MAY -> MediaSeason.SPRING
                in Calendar.JUNE..Calendar.AUGUST -> MediaSeason.SUMMER
                in Calendar.SEPTEMBER..Calendar.NOVEMBER -> MediaSeason.FALL
                else -> MediaSeason.WINTER
            }
        }

        fun <T> debounce(
            waitMs: Long = 500L,
            scope: CoroutineScope,
            destinationFunction: (T) -> Unit,
            dispatcher: CoroutineDispatcher = Dispatchers.IO,
        ): (T) -> Unit {
            var debounceJob: Job? = null
            return { param: T ->
                debounceJob?.cancel()
                debounceJob = scope.launch(dispatcher) {
                    delay(waitMs)
                    destinationFunction(param)
                }
            }
        }
    }
}