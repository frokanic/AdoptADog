package com.example.adoptadog.data.local

import com.example.adoptadog.data.model.DogResponse
import com.example.adoptadog.domain.model.Dog
import io.realm.kotlin.query.RealmResults

interface DogDataSource {
    suspend fun getRandomDog(): DogResponse
    suspend fun getSavedDogs(): RealmResults<Dog>
}

