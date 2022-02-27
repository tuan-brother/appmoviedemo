package com.example.mydemoproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydemoproject.data.model.Movies
import com.example.mydemoproject.data.repository.MovieRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(val movieRepository: MovieRepository) : ViewModel() {
    val listData = MutableLiveData<List<Movies>>()
    val listInsert = MutableLiveData<List<Movies>>()
    val isLastPage = MutableLiveData<Boolean>()
    var pageNumber = 0
    fun getDataMovies(isAdd: Boolean = false) {
        if (isAdd)
            pageNumber++
        else
            pageNumber = 1

        viewModelScope.launch {
            try {
                if (isAdd) {
                    movieRepository.getMovies(page = pageNumber)?.apply {
                        listInsert.value = this.results ?: mutableListOf()
                        this.total_pages?.apply {
                            isLastPage.value = pageNumber >= this
                        }
                    }
                } else {
                    delay(3000)
                    movieRepository.getMovies(page = pageNumber)?.apply {
                        listData.value = this.results ?: mutableListOf()
                        this.total_pages?.apply {
                            isLastPage.value = pageNumber >= this
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.d("TAG123", "${ex.message}:  ")
            }
        }
    }
}