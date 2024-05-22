package com.example.interviewapiapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.interviewapiapp.R
import com.example.interviewapiapp.api.JSONResponse
import com.example.interviewapiapp.api.RetrofitProvider
import com.example.interviewapiapp.databinding.FragmentHomeBinding
import com.example.interviewapiapp.repository.MainRepository
import com.example.interviewapiapp.view.MyAdapter
import com.example.interviewapiapp.view.UserItemClick
import com.example.interviewapiapp.viewmodel.MainViewModel
import com.example.interviewapiapp.viewmodel.MainViewModelFactory

class HomeFragment : Fragment(), UserItemClick {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private val adapter = MyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

            }
        })

        val jsonAPI = RetrofitProvider.jsonAPI

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(MainRepository(jsonAPI))
        )[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter

        viewModel.fetchDataFromAPI()

        viewModel.data.observe(requireActivity()){
            binding.progressbar.visibility = View.GONE
            when(it) {
                is JSONResponse.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is JSONResponse.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is JSONResponse.Success -> {
                    adapter.submitList(it.data)
                }
            }

        }

        binding.btnSearch.setOnClickListener {
            val user_name = binding.edtName.text.toString()
            if(user_name.isBlank()){
                binding.edtName.error = "Name Shouldn't Be Empty"
                Toast.makeText(requireContext(), "Please Enter User's Name", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.filterByName(user_name)

                viewModel.names.observe(requireActivity()){
                    binding.progressbar.visibility = View.GONE
                    when(it){
                        is JSONResponse.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is JSONResponse.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                        }
                        is JSONResponse.Success -> {
                            adapter.submitList(it.data)
                            binding.btnReset.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        binding.btnReset.setOnClickListener {
            viewModel.fetchDataFromAPI()

            viewModel.data.observe(requireActivity()){
                binding.progressbar.visibility = View.GONE
                when(it) {
                    is JSONResponse.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is JSONResponse.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is JSONResponse.Success -> {
                        binding.btnReset.visibility = View.GONE
                        adapter.submitList(it.data)
                    }
                }

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int) {
        val currentUser = adapter.getCurrentUser(position)
        val bundle = Bundle()
        bundle.putString("name", currentUser.name)
        bundle.putString("email", currentUser.email)
        bundle.putString("phone", currentUser.phone)
        bundle.putString("street", currentUser.address.street)
        bundle.putString("suite", currentUser.address.suite)
        bundle.putString("city", currentUser.address.city)
        bundle.putString("zip", currentUser.address.zipcode)
        bundle.putString("lat", currentUser.address.geo.lat)
        bundle.putString("long", currentUser.address.geo.lng)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)

    }

}