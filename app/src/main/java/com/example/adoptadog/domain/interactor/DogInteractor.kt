package com.example.adoptadog.domain.interactor

import android.util.Log
import com.example.adoptadog.domain.model.Dog
import com.example.adoptadog.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

class DogInteractor(private val dogRepository: DogRepository) {

    suspend fun getAndStoreRandomDog() {
        Log.d("STEPSFROM1TO", "5: ${dogRepository.getAndStoreDog()}")
        dogRepository.getAndStoreDog()
    }

    fun getStoredDogs(): Flow<List<Dog>> {
        Log.d("STEPSFROM1TO", "5: ${dogRepository.getStoredDogs()}")
        return dogRepository.getStoredDogs()
    }

    fun getDogById(id: ObjectId): Flow<List<Dog>>  {
        return dogRepository.getDogById(id)
    }
}
