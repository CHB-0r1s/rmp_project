package org.example.service

import org.example.repository.MeasurementRepository
import org.example.models.*

class MeasurementService(private val measurementRepository: MeasurementRepository) {

    fun getMeasurements(username: String): MeasurementsResponse {
        val measurements = measurementRepository.getMeasurements(username)
        return measurements
    }

    fun updateGender(username: String, updateGenderRequest: UpdateGenderRequest): MeasurementsResponse {
        return measurementRepository.updateGender(username, updateGenderRequest)
    }

    fun updateWeight(username: String, updateWeightRequest: UpdateWeightRequest): MeasurementsResponse {
        return measurementRepository.updateWeight(username, updateWeightRequest)
    }

    fun updateHeight(username: String, updateHeightRequest: UpdateHeightRequest): MeasurementsResponse {
        return measurementRepository.updateHeight(username, updateHeightRequest)
    }

    fun updateWaist(username: String, updateWaistRequest: UpdateWaistRequest): MeasurementsResponse {
        return measurementRepository.updateWaist(username, updateWaistRequest)
    }

    fun updateHips(username: String, updateHipsRequest: UpdateHipsRequest): MeasurementsResponse {
        return measurementRepository.updateHips(username, updateHipsRequest)
    }

    fun updateChest(username: String, updateChestRequest: UpdateChestRequest): MeasurementsResponse {
        return measurementRepository.updateChest(username, updateChestRequest)
    }

    fun updateArms(username: String, updateArmsRequest: UpdateArmsRequest): MeasurementsResponse {
        return measurementRepository.updateArms(username, updateArmsRequest)
    }

    fun updateBodyFat(username: String, updateBodyFatRequest: UpdateBodyFatRequest): MeasurementsResponse {
        return measurementRepository.updateBodyFat(username, updateBodyFatRequest)
    }

    fun updateMuscleMass(username: String, updateMuscleMassRequest: UpdateMuscleMassRequest): MeasurementsResponse {
        return measurementRepository.updateMuscleMass(username, updateMuscleMassRequest)
    }

    fun updateBloodGlucose(username: String, updateBloodGlucoseRequest: UpdateBloodGlucoseRequest): MeasurementsResponse {
        return measurementRepository.updateBloodGlucose(username, updateBloodGlucoseRequest)
    }

    fun updateBloodPressureSystolic(username: String, updateBloodPressureSystolicRequest: UpdateBloodPressureSystolicRequest): MeasurementsResponse {
        return measurementRepository.updateBloodPressureSystolic(username, updateBloodPressureSystolicRequest)
    }

    fun updateBloodPressureDiastolic(username: String, updateBloodPressureDiastolicRequest: UpdateBloodPressureDiastolicRequest): MeasurementsResponse {
        return measurementRepository.updateBloodPressureDiastolic(username, updateBloodPressureDiastolicRequest)
    }
}
