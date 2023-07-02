package com.example.adoptadog.data.repository

import android.util.Log
import com.example.adoptadog.data.remote.DogApi
import com.example.adoptadog.domain.model.Dog
import com.example.adoptadog.domain.repository.DogRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class DogRepositoryImpl(val api: DogApi, val realm: Realm) : DogRepository {
    override suspend fun getAndStoreDog() {
        val response = api.getRandomDog().body()
        Log.d("DogRepositoryImpl", "Response: $response")
        if (response != null) {
            val dog = Dog().apply {
                breed = response.message.split("/")[4]
                imageUrl = response.message
            }
            Log.d("DogRepositoryImpl", "Dog to be stored: $dog")
            realm.write {
                val existingDog = this.query<Dog>("breed == $0 AND imageUrl == $1", dog.breed, dog.imageUrl).find().firstOrNull()
                if (existingDog == null) {
                    this.copyToRealm(dog)
                }
            }
            val allDogs = realm.query<Dog>().find()
            Log.d("DogRepositoryImpl", "All dogs after storing: $allDogs")
            // New Log: check if the dog is stored successfully
            val storedDog = realm.query<Dog>("breed == $0 AND imageUrl == $1", dog.breed, dog.imageUrl).find().firstOrNull()
            Log.d("DogRepositoryImpl", "Stored dog: $storedDog")
        } else {
            Log.d("DogRepositoryImpl", "Response is null")
        }
    }




    override fun getStoredDogs(): Flow<List<Dog>> {
        val results = realm.query<Dog>().find()
        Log.d("DogRepositoryImpl", "Stored dogs: $results")
        return results.asFlow().map { it.list }
    }

    override fun getDogById(id: ObjectId): Flow<List<Dog>> {
        return realm.query<Dog>("id == $0", id).asFlow().map { it.list }
    }
}