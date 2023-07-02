package com.example.adoptadog.data.remote

import com.example.adoptadog.data.model.DogResponse
import retrofit2.Response
import retrofit2.http.GET

interface DogApi {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): Response<DogResponse>
}
