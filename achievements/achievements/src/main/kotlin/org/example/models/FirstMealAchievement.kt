package org.example.models

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.dto.ErrorResponse
import org.example.dto.MealDTO
import org.example.enums.Achievement
import java.util.concurrent.TimeUnit

class FirstMealAchievement : AchievementTemplate() {
    override val achievement: Achievement = Achievement.FIRST_FOOD_ENTRY
    
    private val client = HttpClient(Apache) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
    
    private val baseUrl = "http://products:5022"
    
    override suspend fun isAchieved(username: String): Boolean {
        return try {
            println("Checking achievement for user $username")
            println("Using meals service URL: $baseUrl")
            
            val response = client.get("$baseUrl/meals") {
                header("X-User-ID", username)
                header(HttpHeaders.Accept, ContentType.Application.Json)
            }
            
            println("Response status: ${response.status}")
            val responseBody = response.bodyAsText()
            println("Response body: $responseBody")
            
            if (response.status == HttpStatusCode.OK) {
                val meals = Json.decodeFromString<List<MealDTO>>(responseBody)
                println("Decoded meals: $meals")
                println("Number of meals: ${meals.size}")
                val result = meals.isNotEmpty()
                println("Achievement unlocked: $result")
                result
            } else {
                val errorResponse = Json.decodeFromString<ErrorResponse>(responseBody)
                println("Error checking achievement for user $username: ${errorResponse.message}")
                false
            }
        } catch (e: Exception) {
            println("Exception checking achievement for user $username: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}
