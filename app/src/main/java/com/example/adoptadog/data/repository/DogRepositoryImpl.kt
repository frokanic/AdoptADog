package com.example.adoptadog.data.repository

import com.example.adoptadog.data.remote.DogApi
import com.example.adoptadog.domain.model.Dog
import com.example.adoptadog.domain.repository.DogRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class DogRepositoryImpl(val api: DogApi, val realm: Realm) : DogRepository {

    override suspend fun getAndStoreDog() {
        val response = api.getRandomDog().body()
        if (response != null) {
            val dog = Dog().apply {
                breed = response.message.split("/")[4]
                imageUrl = response.message
            }
            realm.write {
                val existingDog = this.query<Dog>("id == $0", dog.id).first()
                if (existingDog == null) {
                    this.copyToRealm(dog)
                }
            }
        }
    }

    override fun getStoredDogs(): Flow<List<Dog>> {
        return realm.query<Dog>().asFlow().map { it.list }
    }

    override fun getDogById(id: ObjectId): Flow<List<Dog>> {
        return realm.query<Dog>("id == $0", id).asFlow().map { it.list }
    }
}