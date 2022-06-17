package com.gb.stopwatch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

const val DELAY_TIME = 20L
const val DEFAULT_TIME = "00:00:000"

class MainViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder
) : ViewModel() {

    private var job: Job? = null

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    private fun startJob() {
        stopJob()
        job = scope.launch {
            while (isActive) {
                stopwatchStateHolder.getStringTimeRepresentation()
                    .collect {
                        _liveData.value = it
                        delay(DELAY_TIME)
                    }
            }
        }
    }

    fun start() {
        startJob()
        stopwatchStateHolder.start()
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        job?.cancel()
    }

    private fun clearValue() {
        _liveData.value = DEFAULT_TIME
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}

