package com.example.adoptadog.data.local

import com.example.adoptadog.data.model.DogResponse
import com.example.adoptadog.data.remote.DogApi
import com.example.adoptadog.domain.model.Dog
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.RealmResults
import javax.inject.Inject


class DogDataSourceImpl @Inject constructor(private val dogApi: DogApi, private val configuration: RealmConfiguration) : DogDataSource {

    override suspend fun getRandomDog(): DogResponse {
        val response = dogApi.getRandomDog()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Error fetching data from API")
        }
    }

    override suspend fun getSavedDogs(): RealmResults<Dog> {
        val realm = Realm.open(configuration)
        return realm.query(Dog::class).find()
    }
}
