package com.example.interviewapiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewapiapp.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val data get() = mainRepository.data

    val names get() = mainRepository.names

    fun fetchDataFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.fetchDataFromAPI()
        }
    }

    fun filterByName(name: String){
        viewModelScope.launch(Dispatchers.IO){
            mainRepository.filterByName(name)
        }
    }

}