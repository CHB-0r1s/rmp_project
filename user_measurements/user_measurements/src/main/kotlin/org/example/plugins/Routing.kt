package org.example.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.models.*
import org.example.service.MeasurementService
import org.example.repository.MeasurementRepository
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.http.*

fun Application.configureRouting() {

    val measurementRepository = MeasurementRepository()
    val measurementService = MeasurementService(measurementRepository)

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        get("/measurements") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@get
            }

            try {
                val measurements = measurementService.getMeasurements(username)
                call.respond(HttpStatusCode.OK, measurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to get measurements: ${e.message}"))
            }
        }

        post("/update-gender") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateGenderRequest>()

            try {
                val updatedMeasurements = measurementService.updateGender(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update gender: ${e.javaClass.name} - ${e.message} ${username} ${request.gender}"))
            }
        }

        post("/update-weight") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateWeightRequest>()

            try {
                val updatedMeasurements = measurementService.updateWeight(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update weight: ${e.message}"))
            }
        }

        post("/update-height") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateHeightRequest>()

            try {
                val updatedMeasurements = measurementService.updateHeight(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update height: ${e.message}"))
            }
        }

        post("/update-waist") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateWaistRequest>()

            try {
                val updatedMeasurements = measurementService.updateWaist(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update waist: ${e.message}"))
            }
        }

        post("/update-hips") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateHipsRequest>()

            try {
                val updatedMeasurements = measurementService.updateHips(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update hips: ${e.message}"))
            }
        }

        post("/update-chest") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateChestRequest>()

            try {
                val updatedMeasurements = measurementService.updateChest(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update chest: ${e.message}"))
            }
        }

        post("/update-body-fat") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateBodyFatRequest>()

            try {
                val updatedMeasurements = measurementService.updateBodyFat(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update body fat: ${e.message}"))
            }
        }

        post("/update-muscle-mass") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateMuscleMassRequest>()

            try {
                val updatedMeasurements = measurementService.updateMuscleMass(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update muscle mass: ${e.message}"))
            }
        }

        post("/update-blood-glucose") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateBloodGlucoseRequest>()

            try {
                val updatedMeasurements = measurementService.updateBloodGlucose(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update blood glucose: ${e.message}"))
            }
        }

        post("/update-blood-pressure-systolic") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateBloodPressureSystolicRequest>()

            try {
                val updatedMeasurements = measurementService.updateBloodPressureSystolic(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update systolic pressure: ${e.message}"))
            }
        }

        post("/update-blood-pressure-diastolic") {
            val username = call.request.headers["X-User-ID"] ?: run {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Missing X-User-ID header"))
                return@post
            }

            val request = call.receive<UpdateBloodPressureDiastolicRequest>()

            try {
                val updatedMeasurements = measurementService.updateBloodPressureDiastolic(username, request)
                call.respond(HttpStatusCode.OK, updatedMeasurements)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Failed to update diastolic pressure: ${e.message}"))
            }
        }
    }
}
