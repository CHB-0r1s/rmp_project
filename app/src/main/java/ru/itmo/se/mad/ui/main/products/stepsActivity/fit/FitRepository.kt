package ru.itmo.se.mad.ui.main.products.stepsActivity.fit

import android.content.Context

interface FitRepository {

    fun hasPermissions(context: Context): Boolean

    fun getFitnessOptions(): Any

    suspend fun getDailyStepCount(context: Context): List<ActivityData>

    suspend fun getStepCount(context: Context, startTime: Long, endTime: Long): List<ActivityData>

    suspend fun getUserInfo(context: Context): UserInfo
}