package ru.itmo.se.mad.ui.main.products.stepsActivity.fit

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.TimeUnit


class FitApiService {
    private val TAG = "FitApiService"

    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()

    fun hasPermissions(context: Context): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return account != null && GoogleSignIn.hasPermissions(account, fitnessOptions)
    }

    fun getFitnessOptions(): FitnessOptions {
        return fitnessOptions
    }

    suspend fun getDailyStepCount(context: Context): List<ActivityData> = withContext(Dispatchers.IO) {
        try {

            val account = GoogleSignIn.getLastSignedInAccount(context)
                ?: throw Exception("Not signed in to Google Account")

            val endTime = System.currentTimeMillis()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = endTime
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startTime = calendar.timeInMillis


            try {
                val historyData = getStepHistoryData(context, account, startTime, endTime)
                if (historyData.isNotEmpty() && historyData[0].steps > 0) {
                    return@withContext historyData
                }
            } catch (e: Exception) {
                throw e;
            }

            val sessionData = getSessionStepData(context, account, startTime, endTime)
            if (sessionData.isNotEmpty() && sessionData[0].steps > 0) {
                return@withContext sessionData
            }

            try {
                val sensorData = getSensorStepData(context, account)
                if (sensorData > 0) {
                    return@withContext listOf(ActivityData(startTime, endTime, sensorData))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error reading from Sensors API", e)
            }

            val lastResortData = getAnyStepData(context, account, startTime, endTime)
            if (lastResortData.isNotEmpty()) {
                return@withContext lastResortData
            }

            return@withContext listOf(ActivityData(startTime, endTime, 0))

        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getStepHistoryData(
        context: Context,
        account: GoogleSignInAccount,
        startTime: Long,
        endTime: Long
    ): List<ActivityData> {
        val dataRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()

        val response = Fitness.getHistoryClient(context, account)
            .readData(dataRequest)
            .await()

        val activityDataList = mutableListOf<ActivityData>()

        var totalSteps = 0

        response.buckets.flatMap { bucket ->
            bucket.dataSets
        }.forEach { dataSet ->
            dataSet.dataPoints.forEach { point ->
                val steps = point.getValue(Field.FIELD_STEPS).asInt()

                totalSteps += steps
            }
        }

        activityDataList.add(
            ActivityData(
                startTime = startTime,
                endTime = endTime,
                steps = totalSteps
            )
        )

        return activityDataList
    }

    private suspend fun getSessionStepData(
        context: Context,
        account: GoogleSignInAccount,
        startTime: Long,
        endTime: Long
    ): List<ActivityData> {
        try {
            val dataSet = Fitness.getHistoryClient(context, account)
                .readData(
                    DataReadRequest.Builder()
                        .read(DataType.TYPE_STEP_COUNT_DELTA)
                        .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                        .build()
                )
                .await()


            var totalSteps = 0

            dataSet.dataSets.forEach { ds ->
                ds.dataPoints.forEach { point ->
                    val steps = point.getValue(Field.FIELD_STEPS).asInt()
                    totalSteps += steps
                }
            }

            return listOf(ActivityData(startTime, endTime, totalSteps))
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private suspend fun getSensorStepData(
        context: Context,
        account: GoogleSignInAccount
    ): Int {
        try {
            val total = Fitness.getHistoryClient(context, account)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .await()

            var stepCount = 0
            if (total.dataPoints.isNotEmpty()) {
                stepCount = total.dataPoints[0].getValue(Field.FIELD_STEPS).asInt()
            }

            return stepCount
        } catch (e: Exception) {
            return 0
        }
    }

    private suspend fun getAnyStepData(
        context: Context,
        account: GoogleSignInAccount,
        startTime: Long,
        endTime: Long
    ): List<ActivityData> {
        try {
            val total = Fitness.getHistoryClient(context, account)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .await()

            var stepCount = 0
            if (total.dataPoints.isNotEmpty()) {
                stepCount = total.dataPoints[0].getValue(Field.FIELD_STEPS).asInt()
                return listOf(ActivityData(startTime, endTime, stepCount))
            }

            return emptyList()
        } catch (e: Exception) {
            return emptyList()
        }
    }

    suspend fun getStepCount(context: Context, startTime: Long, endTime: Long): List<ActivityData> =
        withContext(Dispatchers.IO) {
            try {
                val account = GoogleSignIn.getLastSignedInAccount(context)
                    ?: throw Exception("Not signed in to Google Account")

                val dataRequest = DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                    .bucketByTime(1, TimeUnit.DAYS)
                    .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                    .build()

                val response = Fitness.getHistoryClient(context, account)
                    .readData(dataRequest)
                    .await()

                val activityDataList = mutableListOf<ActivityData>()

                response.buckets.forEach { bucket ->
                    var dailySteps = 0
                    bucket.dataSets.forEach { dataSet ->
                        dataSet.dataPoints.forEach { point ->
                            val steps = point.getValue(Field.FIELD_STEPS).asInt()
                            dailySteps += steps
                        }
                    }

                    if (dailySteps > 0) {
                        activityDataList.add(
                            ActivityData(
                                startTime = bucket.getStartTime(TimeUnit.MILLISECONDS),
                                endTime = bucket.getEndTime(TimeUnit.MILLISECONDS),
                                steps = dailySteps
                            )
                        )
                    }
                }

                if (activityDataList.isEmpty()) {
                    activityDataList.add(ActivityData(startTime, endTime, 0))
                }

                activityDataList
            } catch (e: Exception) {
                Log.e(TAG, "Error getting step count", e)
                throw e
            }
        }

    suspend fun getUserInfo(context: Context): UserInfo = withContext(Dispatchers.IO) {
        try {
            val account = GoogleSignIn.getLastSignedInAccount(context)
                ?: throw Exception("Not signed in to Google Account")

            UserInfo(
                id = account.id ?: "",
                email = account.email ?: "",
                name = account.displayName ?: ""
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user info", e)
            throw e
        }
    }
}