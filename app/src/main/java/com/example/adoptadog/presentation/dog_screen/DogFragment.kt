package com.example.adoptadog.presentation.dog_screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.adoptadog.databinding.FragmentDogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogFragment : Fragment() {
    private var _binding: FragmentDogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DogViewModel by viewModels() // ensure ViewModel factory is set up

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogBinding.inflate(inflater, container, false)

        val dogId = DogFragmentArgs.fromBundle(requireArguments()).dogId
        viewModel.loadDog(dogId)
        Log.d("DOGFRAGMENTOBSERVE", viewModel.loadDog(dogId).toString())

        viewModel.dog.observe(viewLifecycleOwner) { dog ->
            Log.d("DOGFRAGMENTOBSERVE", dog.breed)
            binding.title.text = dog?.breed
            binding.image.load(dog?.imageUrl) {
                crossfade(true)
            }
        }


        setupClickListeners()

        return binding.root
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
