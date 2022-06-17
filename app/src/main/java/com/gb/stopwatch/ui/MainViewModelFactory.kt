package com.gb.stopwatch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope

class MainViewModelFactory(
    private val stopwatchStateHolder: StopwatchStateHolder
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            stopwatchStateHolder
        ) as T
    }
}