package com.example.adoptadog.presentation.dog_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptadog.domain.interactor.DogInteractor
import com.example.adoptadog.domain.model.Dog
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class DogViewModel(private val dogInteractors: DogInteractor) : ViewModel() {
    val dog = MutableLiveData<Dog?>()

    fun loadDog(id: String) {
        val objectId = ObjectId(id)
        viewModelScope.launch {
            dogInteractors.getDogById(objectId).collect { dogData ->
                dog.value = dogData[0]
            }
        }
    }
}

