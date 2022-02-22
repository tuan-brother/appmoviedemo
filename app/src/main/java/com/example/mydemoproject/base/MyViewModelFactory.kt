package com.example.mydemoproject.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydemoproject.data.remote.ApiHelper
import com.example.mydemoproject.data.repository.MovieRepository
import com.example.mydemoproject.viewmodel.MainViewModel

class MyViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(MovieRepository(apiHelper = apiHelper)) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}