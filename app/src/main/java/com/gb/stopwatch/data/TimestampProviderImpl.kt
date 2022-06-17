package com.gb.stopwatch.data

import com.gb.stopwatch.domain.usecase.TimestampProvider

class TimestampProviderImpl : TimestampProvider {
    override fun getMilliseconds(): Long {
        return System.currentTimeMillis()
    }
}