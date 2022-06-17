package com.gb.stopwatch.data

const val MILLISECONDS_LENGTH = 3
const val SECONDS_LENGTH = 2
const val MINUTES_LENGTH = 2
const val HOURS_LENGTH = 2

const val MILLISECONDS_AND_SECONDS_COEFFICIENT = 1000
const val FORMATTING_COEFFICIENT = 1000

class TimestampMillisecondsFormatter {

    fun format(timestamp: Long): String {
        val millisecondsFormatted =
            (timestamp % MILLISECONDS_AND_SECONDS_COEFFICIENT).pad(MILLISECONDS_LENGTH)
        val seconds = timestamp / MILLISECONDS_AND_SECONDS_COEFFICIENT
        val secondsFormatted = (seconds % FORMATTING_COEFFICIENT).pad(SECONDS_LENGTH)
        val minutes = seconds / FORMATTING_COEFFICIENT
        val minutesFormatted = (minutes % FORMATTING_COEFFICIENT).pad(MINUTES_LENGTH)
        val hours = minutes / FORMATTING_COEFFICIENT
        return if (hours > 0) {
            val hoursFormatted = (minutes / FORMATTING_COEFFICIENT).pad(HOURS_LENGTH)
            "$hoursFormatted:$minutesFormatted:$secondsFormatted"
        } else {
            "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
        }
    }

    private fun Long.pad(length: Int) = this.toString().padStart(length, '0')
}