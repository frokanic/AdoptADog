package com.example.adoptadog.presentation.dog_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adoptadog.domain.model.Dog
import com.example.adoptadog.domain.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {
    var dog = MutableLiveData<Dog>()

    fun loadDog(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id.isNotEmpty()) {
                try {
                    val objectId = ObjectId(hexString = id)
                    repository.getDogById(objectId).collect { dogList ->
                        if (dogList.isNotEmpty()) {
                            dog.postValue(dogList[0])
                        } else {
                            // No dog was found with the provided ID
                            Log.e("DogViewModel", "No dog was found with ID: $id")
                        }
                    }
                } catch (e: IllegalArgumentException) {
                    // Log the exception or show a message to the user
                    Log.e("DogViewModel", "Invalid ObjectId: $id", e)
                }
            }
        }
    }

}

