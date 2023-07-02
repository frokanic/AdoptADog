package com.example.adoptadog.presentation.all_dogs_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adoptadog.databinding.FragmentAllDogsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AllDogsFragment : Fragment() {

    private var _binding: FragmentAllDogsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllDogsViewModel by viewModels()

    val adapter = AllDogsAdapter(emptyList()) { dog ->
        val action = AllDogsFragmentDirections.actionAllDogsFragmentToDogFragment(dog.id.toHexString())
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDogsBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        observeDogs()
        setupClickListeners()
        observeNetworkStatus()

        return binding.root
    }

    private fun observeDogs() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dogs.collect { dogs ->
                adapter.updateDogs(dogs)
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnGetADog.setOnClickListener {
            if (viewModel.isNetworkAvailable.value) {
                viewModel.getRandomDogAndStore()
            } else {
                showNoInternetConnectionToast()
            }
        }
    }

    private fun showNoInternetConnectionToast() {
        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
    }

    private fun observeNetworkStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isNetworkAvailable.collect { isNetworkAvailable ->
                if (viewModel.isNetworkAvailableInitialized.value == true) {
                    binding.btnGetADog.isEnabled = isNetworkAvailable == true
                    if (isNetworkAvailable == false) {
                        showNoInternetConnectionToast()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


