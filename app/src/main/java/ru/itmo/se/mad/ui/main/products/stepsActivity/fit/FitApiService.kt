package ru.itmo.se.mad.ui.main.products.stepsActivity.fit

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


class FitApiService {
    private val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo1MDAwIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6NTAwMCIsInVzZXJuYW1lIjoidXNlciJ9.PXFU57PS94Da36MEVmnbSUIdo9UrJuRCP496Bipn8a0"

    data class StepsResponse(
        val steps: Int,
        val goal: Int
    )

    data class GoalRequest(
        val goal: Int
    )

    data class StepsRequest(
        val steps: Int
    )

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(original.method(), original.body())
            
            chain.proceed(requestBuilder.build())
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5013")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val activityApi = retrofit.create(FitRepository::class.java)

    suspend fun getUserSteps(): Int = withContext(Dispatchers.IO) {
        val call = activityApi.getSteps()
        var steps = 0
        
        try {
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                steps = response.body()!!.steps
            }
        } catch (e: Exception) {
            Log.e("FitApiService", "Error getting steps", e)
        }
        
        steps
    }

    suspend fun setDailyGoal(goal: Int): Boolean = withContext(Dispatchers.IO) {
        val call = activityApi.setDailyGoal(GoalRequest(goal))
        
        try {
            val response = call.execute()
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("FitApiService", "Error setting daily goal", e)
            false
        }
    }

    suspend fun setSteps(steps: Int): Boolean = withContext(Dispatchers.IO) {
        val call = activityApi.setSteps(StepsRequest(steps))
        
        try {
            val response = call.execute()
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("FitApiService", "Error setting steps", e)
            false
        }
    }

    suspend fun getUserStepsAndGoal(): Pair<Int, Int> = withContext(Dispatchers.IO) {
        val call = activityApi.getSteps()
        var steps = 0
        var goal = 5000 // Default goal
        
        try {
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                steps = response.body()!!.steps
                goal = response.body()!!.goal
                Log.v("getUserStepsAndGoal", response.toString())
            }
            Log.v("getUserStepsAndGoal", response.toString())
        } catch (e: Exception) {
            Log.e("FitApiService", "Error getting steps and goal", e)
        }
        
        Pair(steps, goal)
    }
}