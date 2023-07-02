package com.example.adoptadog.di

import com.example.adoptadog.domain.model.Dog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                Dog::class
            )
        )
            .compactOnLaunch()
            .build()
        return Realm.open(config)
    }



}