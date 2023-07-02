package com.example.adoptadog.domain.repository

import com.example.adoptadog.domain.model.Dog
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface DogRepository {

    suspend fun getAndStoreDog()
    fun getStoredDogs(): Flow<List<Dog>>
    fun getDogById(id: ObjectId): Flow<List<Dog>>
}

