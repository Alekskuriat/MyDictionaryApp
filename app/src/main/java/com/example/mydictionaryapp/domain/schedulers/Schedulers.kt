package com.example.popularlibraries.domain.schedulers

import io.reactivex.rxjava3.core.Scheduler

interface Schedulers {

    fun background(): Scheduler
    fun main(): Scheduler

}