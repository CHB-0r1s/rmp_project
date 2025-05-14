package org.example.repository

import org.example.Database
import org.example.models.*
import java.sql.Connection
import java.sql.Timestamp
import java.time.LocalDateTime

class MeasurementRepository {

    fun getMeasurements(username: String): MeasurementsResponse {
        Database.getConnection().use { connection ->
            connection.prepareStatement("""
                SELECT *
                FROM user_measurements WHERE username = ?
            """).use { statement ->
                statement.setString(1, username)
                statement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return MeasurementsResponse(
                            gender = resultSet.getString("gender"),
                            weight = resultSet.getFloat("weight"),
                            height = resultSet.getFloat("height"),
                            waist = resultSet.getFloat("waist"),
                            hips = resultSet.getFloat("hips"),
                            chest = resultSet.getFloat("chest"),
                            arms = resultSet.getFloat("arms"),
                            bodyFat = resultSet.getFloat("body_fat"),
                            muscleMass = resultSet.getFloat("muscle_mass"),
                            bloodGlucose = resultSet.getFloat("blood_glucose"),
                            bloodPressureSystolic = resultSet.getInt("bp_systolic"),
                            bloodPressureDiastolic = resultSet.getInt("bp_diastolic"),
                            measuredAt = resultSet.getString("measured_at")
                        )
                    }
                }
            }
        }

        return MeasurementsResponse(
            gender = "",
            weight = 0f,
            height = 0f,
            waist = 0f,
            hips = 0f,
            chest = 0f,
            arms = 0f,
            bodyFat = 0f,
            muscleMass = 0f,
            bloodGlucose = 0f,
            bloodPressureSystolic = 0,
            bloodPressureDiastolic = 0,
            measuredAt = ""
        )
    }

    fun updateGender(username: String, updateGenderRequest: UpdateGenderRequest): MeasurementsResponse {
        return updateMeasurement(username, "gender", updateGenderRequest.gender)
    }

    fun updateWeight(username: String, updateWeightRequest: UpdateWeightRequest): MeasurementsResponse {
        return updateMeasurement(username, "weight", updateWeightRequest.weight)
    }

    fun updateHeight(username: String, updateHeightRequest: UpdateHeightRequest): MeasurementsResponse {
        return updateMeasurement(username, "height", updateHeightRequest.height)
    }

    fun updateWaist(username: String, updateWaistRequest: UpdateWaistRequest): MeasurementsResponse {
        return updateMeasurement(username, "waist", updateWaistRequest.waist)
    }

    fun updateHips(username: String, updateHipsRequest: UpdateHipsRequest): MeasurementsResponse {
        return updateMeasurement(username, "hips", updateHipsRequest.hips)
    }

    fun updateChest(username: String, updateChestRequest: UpdateChestRequest): MeasurementsResponse {
        return updateMeasurement(username, "chest", updateChestRequest.chest)
    }

    fun updateArms(username: String, updateArmsRequest: UpdateArmsRequest): MeasurementsResponse {
        return updateMeasurement(username, "arms", updateArmsRequest.arms)
    }

    fun updateBodyFat(username: String, updateBodyFatRequest: UpdateBodyFatRequest): MeasurementsResponse {
        return updateMeasurement(username, "body_fat", updateBodyFatRequest.bodyFat)
    }

    fun updateMuscleMass(username: String, updateMuscleMassRequest: UpdateMuscleMassRequest): MeasurementsResponse {
        return updateMeasurement(username, "muscle_mass", updateMuscleMassRequest.muscleMass)
    }

    fun updateBloodGlucose(username: String, updateBloodGlucoseRequest: UpdateBloodGlucoseRequest): MeasurementsResponse {
        return updateMeasurement(username, "blood_glucose", updateBloodGlucoseRequest.bloodGlucose)
    }

    fun updateBloodPressureSystolic(username: String, updateBloodPressureSystolicRequest: UpdateBloodPressureSystolicRequest): MeasurementsResponse {
        return updateMeasurement(username, "bp_systolic", updateBloodPressureSystolicRequest.systolic)
    }

    fun updateBloodPressureDiastolic(username: String, updateBloodPressureDiastolicRequest: UpdateBloodPressureDiastolicRequest): MeasurementsResponse {
        return updateMeasurement(username, "bp_diastolic", updateBloodPressureDiastolicRequest.diastolic)
    }

    private fun updateMeasurement(username: String, column: String, value: Any): MeasurementsResponse {
        val todayDate = Timestamp.valueOf(LocalDateTime.now())

        Database.getConnection().use { connection ->
            val sql = "UPDATE user_measurements SET $column = ?, measured_at = ? WHERE username = ?"
            connection.prepareStatement(sql).use { updateStmt ->
                when (value) {
                    is Float -> updateStmt.setFloat(1, value)
                    is Int -> updateStmt.setInt(1, value)
                    is String -> updateStmt.setString(1, value)
                }
                updateStmt.setTimestamp(2, todayDate)
                updateStmt.setString(3, username)

                val updatedRows = updateStmt.executeUpdate()
                if (updatedRows == 0) {
                    connection.prepareStatement("""
                        INSERT INTO user_measurements (username, $column, measured_at)
                        VALUES (?, ?, ?)
                    """).use { insertStmt ->
                        when (value) {
                            is Float -> insertStmt.setFloat(2, value)
                            is Int -> insertStmt.setInt(2, value)
                            is String -> insertStmt.setString(2, value)
                        }
                        insertStmt.setString(1, username)
                        insertStmt.setTimestamp(3, todayDate)
                        insertStmt.executeUpdate()
                    }
                }
            }
        }

        return getMeasurements(username)
    }
}
