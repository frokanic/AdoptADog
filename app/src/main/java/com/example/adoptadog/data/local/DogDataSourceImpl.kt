package com.example.adoptadog.data.local

import android.util.Log
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
        Log.d("DogDataSourceImpl", "API response: $response")
        if (response.isSuccessful) {
            val dogResponse = response.body()!!
            Log.d("DogDataSourceImpl", "Random dog response: $dogResponse")
            return dogResponse
        } else {
            throw Exception("Error fetching data from API")
        }
    }

    override suspend fun getSavedDogs(): RealmResults<Dog> {
        val realm = Realm.open(configuration)
        val result = realm.query(Dog::class).find()
        Log.d("DogDataSourceImpl", "Saved dogs: $result")
        return result
    }
}
