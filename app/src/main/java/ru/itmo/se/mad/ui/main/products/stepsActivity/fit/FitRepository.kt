package ru.itmo.se.mad.ui.main.products.stepsActivity.fit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface FitRepository {
    @GET("activity/steps")
    fun getSteps(): Call<FitApiService.StepsResponse>
    
    @POST("activity/set-steps")
    fun setDailyGoal(@Body goal: FitApiService.GoalRequest): Call<Void>

    @POST("activity/set-steps")
    fun setSteps(@Body stepsData: FitApiService.StepsRequest): Call<Void>
}