package com.example.interviewapiapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.interviewapiapp.R
import com.example.interviewapiapp.api.RetrofitProvider
import com.example.interviewapiapp.databinding.FragmentLaunchBinding
import com.example.interviewapiapp.repository.MainRepository
import com.example.interviewapiapp.viewmodel.MainViewModel
import com.example.interviewapiapp.viewmodel.MainViewModelFactory

class LaunchFragment : Fragment() {

    private var _binding: FragmentLaunchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonAPI = RetrofitProvider.jsonAPI

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(MainRepository(jsonAPI))
        )[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_launchFragment_to_homeFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}