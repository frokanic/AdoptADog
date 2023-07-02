package com.example.adoptadog.presentation.all_dogs_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptadog.domain.interactor.DogInteractor
import com.example.adoptadog.domain.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllDogsViewModel @Inject constructor(private val dogInteractors: DogInteractor) : ViewModel() {
    val dogs = MutableLiveData<List<Dog>>()

    fun loadDogs() {
        viewModelScope.launch {
            dogInteractors.getStoredDogs().collect { dogList ->
                dogs.value = dogList
            }
        }
    }

    fun getRandomDogAndStore() {
        viewModelScope.launch {
            dogInteractors.getAndStoreRandomDog()
            loadDogs() // Load dogs again after a new dog is added.
        }
    }
}


