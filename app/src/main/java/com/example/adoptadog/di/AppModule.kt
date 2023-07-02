package com.example.adoptadog.di

import com.example.adoptadog.data.remote.DogApi
import com.example.adoptadog.data.repository.DogRepositoryImpl
import com.example.adoptadog.domain.interactor.DogInteractor
import com.example.adoptadog.domain.model.Dog
import com.example.adoptadog.domain.repository.DogRepository
import com.example.adoptadog.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
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

    @Singleton
    @Provides
    fun provideDogApi(): DogApi {
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(DogApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDogRepository(api: DogApi, realm: Realm): DogRepository {
        return DogRepositoryImpl(api, realm)
    }

    @Singleton
    @Provides
    fun provideDogInteractor(dogRepository: DogRepository): DogInteractor {
        return DogInteractor(dogRepository)
    }


}