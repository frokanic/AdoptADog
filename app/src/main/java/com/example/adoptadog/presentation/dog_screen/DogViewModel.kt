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
    val TAG = "DogViewModel"

    fun loadDog(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id.isNotEmpty()) {
                try {
                    val objectId = ObjectId(hexString = id) // It should work now
                    repository.getDogById(objectId).collect { dogList ->
                        if (dogList.isNotEmpty()) {
                            dog.postValue(dogList[0])
                        } else {
                            Log.e(TAG, "No dog was found with ID: $id")
                        }
                    }
                } catch (e: IllegalArgumentException) {
                    Log.e(TAG, "Invalid ObjectId: $id", e)
                }
            }
        }
    }
}

