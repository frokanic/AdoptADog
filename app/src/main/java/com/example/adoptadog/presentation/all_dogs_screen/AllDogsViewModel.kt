package com.example.adoptadog.presentation.all_dogs_screen

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptadog.domain.connectivity.ConnectivityObserver
import com.example.adoptadog.domain.interactor.DogInteractor
import com.example.adoptadog.domain.model.Dog
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllDogsViewModel @Inject constructor(
    private val dogInteractors: DogInteractor,
    private val connectivityObserver: ConnectivityObserver,
    ) : ViewModel() {

    private val _dogs = MutableStateFlow<List<Dog>>(emptyList())
    val dogs: StateFlow<List<Dog>> = _dogs

    val isNetworkAvailableInitialized = MutableLiveData<Boolean>(false)
    private val _isNetworkAvailable = MutableStateFlow(false)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    init {
        loadDogs()
        observeNetworkStatus()
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

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                when (status) {
                    ConnectivityObserver.Status.Available -> {
                        isNetworkAvailableInitialized.value = true
                        _isNetworkAvailable.value = true
                    }
                    else -> {
                        isNetworkAvailableInitialized.value = true
                        _isNetworkAvailable.value = false
                    }
                }
            }
        }
    }
}


