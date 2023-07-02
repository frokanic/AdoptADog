package com.example.adoptadog.presentation.all_dogs_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptadog.domain.interactor.DogInteractor
import com.example.adoptadog.domain.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllDogsViewModel @Inject constructor(private val dogInteractors: DogInteractor) : ViewModel() {

    private val _dogs = MutableStateFlow<List<Dog>>(emptyList())
    val dogs: StateFlow<List<Dog>> = _dogs

    init {
        loadDogs()
    }

    private fun loadDogs() {
        viewModelScope.launch {
            dogInteractors.getStoredDogs().collect { dogList ->
                _dogs.value = dogList
            }
        }
    }

    fun getRandomDogAndStore() {
        viewModelScope.launch {
            dogInteractors.getAndStoreRandomDog()
        }
    }
}


