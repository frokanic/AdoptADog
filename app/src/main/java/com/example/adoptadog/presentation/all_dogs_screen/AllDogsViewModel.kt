package com.example.adoptadog.presentation.all_dogs_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptadog.domain.interactor.DogInteractor
import com.example.adoptadog.domain.model.Dog
import kotlinx.coroutines.launch

class AllDogsViewModel(private val dogInteractors: DogInteractor) : ViewModel() {
    val dogs = MutableLiveData<List<Dog>>()

    fun loadDogs() {
        viewModelScope.launch {
            dogInteractors.getStoredDogs().collect { dogList ->
                dogs.value = dogList
            }
        }
    }
}
