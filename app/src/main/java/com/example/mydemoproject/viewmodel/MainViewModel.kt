package com.example.mydemoproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydemoproject.data.model.Movies
import com.example.mydemoproject.data.repository.MovieRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(val movieRepository: MovieRepository) : ViewModel() {
    val listData = MutableLiveData<List<Movies>>()

    fun getDataMovies() {
        viewModelScope.launch {
            try {
                listData.value =
                    movieRepository.getMovies()?.results ?: mutableListOf()
            } catch (ex: Exception) {
                Log.d("TAG123", "${ex.message}:  ")
            }
        }
    }
}