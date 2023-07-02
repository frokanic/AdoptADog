package com.example.adoptadog.domain.repository

import com.example.adoptadog.domain.model.Dog
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface DogRepository {

    suspend fun getAndStoreDog()
    fun getStoredDogs(): Flow<List<Dog>>
    fun getDogById(id: ObjectId): Flow<List<Dog>>
}

