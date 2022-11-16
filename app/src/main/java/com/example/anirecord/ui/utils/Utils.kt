package com.example.anirecord.ui.utils

import com.example.anirecord.type.MediaSeason
import java.util.*

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
    }
}