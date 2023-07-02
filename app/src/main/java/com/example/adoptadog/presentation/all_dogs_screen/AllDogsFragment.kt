package com.example.adoptadog.presentation.all_dogs_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.adoptadog.R
import com.example.adoptadog.databinding.FragmentAllDogsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllDogsFragment : Fragment() {
    private var _binding: FragmentAllDogsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllDogsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllDogsBinding.inflate(inflater, container, false)
        val adapter = AllDogsAdapter(emptyList()) { dog ->
            val action = AllDogsFragmentDirections.actionAllDogsFragmentToDogFragment(dog.id.toString())
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.loadDogs()
        viewModel.dogs.observe(viewLifecycleOwner) { dogs ->
            adapter.dogs = dogs
            adapter.notifyDataSetChanged()
        }

        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {
        binding.btnGetADog.setOnClickListener {
            viewModel.getRandomDogAndStore()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}