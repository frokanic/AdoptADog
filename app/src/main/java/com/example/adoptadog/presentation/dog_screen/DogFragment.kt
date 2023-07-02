package com.example.adoptadog.presentation.dog_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.example.adoptadog.R
import com.example.adoptadog.databinding.FragmentDogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogFragment : Fragment() {
    private var _binding: FragmentDogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DogViewModel by viewModels() // ensure ViewModel factory is set up

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDogBinding.inflate(inflater, container, false)

        val dogId = DogFragmentArgs.fromBundle(requireArguments()).dogId
        viewModel.loadDog(dogId)
        viewModel.dog.observe(viewLifecycleOwner) { dog ->
            binding.title.text = dog?.breed
            binding.image.load(dog?.imageUrl) {
                crossfade(true)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
