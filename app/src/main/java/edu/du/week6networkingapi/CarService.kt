package edu.du.week6networkingapi

import edu.du.week6networkingapi.model.CarJSONModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CarService {
    @POST("cars")
    suspend fun createCar(@Body requestBody: RequestBody): Response<CarJSONModel>

    @GET("cars")
    suspend fun getCars(): Response<List<CarJSONModel>>

    @GET("cars/{id}")
    suspend fun getCars(@Path("id") id: String): Response<CarJSONModel>

    @PUT("cars/{id}")
    suspend fun updateCar(@Path("id") id: String, @Body requestBody: RequestBody): Response<CarJSONModel>

    @DELETE("cars/{id}")
    suspend fun deleteCar(@Path("id") id: String): Response<CarJSONModel>

}