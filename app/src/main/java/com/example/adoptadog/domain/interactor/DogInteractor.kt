package com.example.adoptadog.domain.interactor

import com.example.adoptadog.domain.model.Dog
import com.example.adoptadog.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

class DogInteractor(private val dogRepository: DogRepository) {

    suspend fun getAndStoreRandomDog() {
        dogRepository.getAndStoreDog()
    }

    fun getStoredDogs(): Flow<List<Dog>> {
        return dogRepository.getStoredDogs()
    }

    fun getDogById(id: ObjectId): Flow<List<Dog>>  {
        return dogRepository.getDogById(id)
    }
}
