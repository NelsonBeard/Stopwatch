package com.gb.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.stopwatch.data.ElapsedTimeCalculator
import com.gb.stopwatch.data.StopwatchStateCalculator
import com.gb.stopwatch.data.TimestampMillisecondsFormatter
import com.gb.stopwatch.data.TimestampProviderImpl
import com.gb.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainViewModel
    private lateinit var stopwatchStateHolder: StopwatchStateHolder

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val timestampProvider = TimestampProviderImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        listenForClick()
    }

    private fun initViewModel() {
        stopwatchStateHolder = StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        )

        vm = MainViewModelFactory(
            stopwatchStateHolder,
            scope
        ).create(MainViewModel::class.java)
    }

    private fun listenForClick() {

        vm.liveData.observe(this) {
            binding.textTime.text = it
        }

        binding.buttonStart.setOnClickListener {
            vm.start()
        }

        binding.buttonPause.setOnClickListener {
            vm.pause()
        }

        binding.buttonStop.setOnClickListener {
            vm.stop()
        }
    }
}