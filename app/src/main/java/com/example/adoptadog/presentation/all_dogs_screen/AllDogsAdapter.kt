package com.example.adoptadog.presentation.all_dogs_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adoptadog.databinding.DogItemBinding
import com.example.adoptadog.domain.model.Dog

class AllDogsAdapter(
    var dogs: List<Dog>,
    private val onDogClicked: (Dog) -> Unit
) : RecyclerView.Adapter<AllDogsAdapter.DogViewHolder>() {

    fun updateDogs(newDogs: List<Dog>) {
        this.dogs = newDogs
        notifyDataSetChanged()
    }


    inner class DogViewHolder(private val itemBinding: DogItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dog: Dog) {
            itemBinding.dogName.text = dog.breed
            itemBinding.root.setOnClickListener {
                onDogClicked(dog)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogs[position])
    }

    override fun getItemCount(): Int {
        return dogs.size
    }
}
