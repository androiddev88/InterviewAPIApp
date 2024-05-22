package com.example.interviewapiapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.interviewapiapp.api.APIInterface
import com.example.interviewapiapp.api.JSONResponse
import com.example.interviewapiapp.model.JSONUsers

class MainRepository(private val apiInterface: APIInterface) {

    private val _data = MutableLiveData<JSONResponse<List<JSONUsers>>>()
    val data: LiveData<JSONResponse<List<JSONUsers>>> get() = _data

    private val _names = MutableLiveData<JSONResponse<ArrayList<JSONUsers>>>()
    val names: LiveData<JSONResponse<ArrayList<JSONUsers>>> get() = _names

    val nameList = ArrayList<JSONUsers>()

    suspend fun fetchDataFromAPI() {
        _data.postValue(JSONResponse.Loading())

        try {
            val myResponse = apiInterface.fetchDataFromAPI()
            if(myResponse.isSuccessful) {
                val list = myResponse.body()!!
                _data.postValue(JSONResponse.Success(list))
            }else {
                _data.postValue(JSONResponse.Error("Something Went Wrong"))
            }
        } catch (e: Exception) {
            _data.postValue(JSONResponse.Error(e.message.toString()))
        }
    }

    suspend fun filterByName(name: String) {
        _data.postValue(JSONResponse.Loading())

        try {
            val myResponse = apiInterface.fetchDataFromAPI()
            if(myResponse.isSuccessful) {
                val list = myResponse.body()!!
                val pattern = Regex(name.lowercase())
                for(index in 0 ..(list.size)-1){
                    if(pattern.find(list.get(index).name.lowercase(), 0) != null){
                        nameList.add(list.get(index))
                    }
                }
                if(nameList.size == 0){
                    _names.postValue(JSONResponse.Error("No Such Username Found"))
                }else{
                    _names.postValue(JSONResponse.Success(nameList))
                }

            }else {
                _names.postValue(JSONResponse.Error("Something Went Wrong"))
            }
        } catch (e: Exception) {
            _names.postValue(JSONResponse.Error(e.message.toString()))
        }
    }
}