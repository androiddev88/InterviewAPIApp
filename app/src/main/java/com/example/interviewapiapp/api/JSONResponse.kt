package com.example.interviewapiapp.api

sealed class JSONResponse<T>(val data: T?=null, val message: String?=null) {
    class Loading<T> : JSONResponse<T>()
    class Success<T>(data: T) : JSONResponse<T>(data = data)
    class Error<T>(message: String) : JSONResponse<T>(message = message)
}