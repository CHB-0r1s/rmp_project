package ru.itmo.se.mad.ui.main.products.stepsActivity.fit

import android.content.Context
import com.google.android.gms.fitness.FitnessOptions


class FitRepositoryImpl(private val fitApiService: FitApiService) : FitRepository {

    override fun hasPermissions(context: Context): Boolean {
        return fitApiService.hasPermissions(context)
    }

    override fun getFitnessOptions(): FitnessOptions {
        return fitApiService.getFitnessOptions()
    }

    override suspend fun getDailyStepCount(context: Context): List<ActivityData> {
        return fitApiService.getDailyStepCount(context)
    }

    override suspend fun getStepCount(context: Context, startTime: Long, endTime: Long): List<ActivityData> {
        return fitApiService.getStepCount(context, startTime, endTime)
    }

    override suspend fun getUserInfo(context: Context): UserInfo {
        return fitApiService.getUserInfo(context)
    }
}

data class UserInfo(
    val id: String,
    val email: String,
    val name: String
)

data class ActivityData(
    val startTime: Long,
    val endTime: Long,
    val steps: Int
)