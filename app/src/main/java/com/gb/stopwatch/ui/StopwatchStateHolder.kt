package com.gb.stopwatch.ui

import com.gb.stopwatch.data.ElapsedTimeCalculator
import com.gb.stopwatch.data.StopwatchState
import com.gb.stopwatch.data.StopwatchStateCalculator
import com.gb.stopwatch.data.TimestampMillisecondsFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) {
    var currentState: StopwatchState = StopwatchState.Paused(0)
        private set

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    fun getStringTimeRepresentation(): Flow<String> {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return flow { emit(timestampMillisecondsFormatter.format(elapsedTime)) }
    }
}