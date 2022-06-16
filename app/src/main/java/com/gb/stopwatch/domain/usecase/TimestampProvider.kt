package com.gb.stopwatch.domain.usecase

interface TimestampProvider {
    fun getMilliseconds(): Long
}