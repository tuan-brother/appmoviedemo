package com.example.mydemoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mydemoproject.base.MyViewModelFactory
import com.example.mydemoproject.databinding.ActivityMainBinding
import com.example.mydemoproject.data.remote.ApiHelper
import com.example.mydemoproject.data.remote.RetrofitBuilder
import com.example.mydemoproject.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupViewModel()
    }

    fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory(ApiHelper(RetrofitBuilder().apiService)))
                .get(MainViewModel::class.java).apply {
                    getDataMovies()
                    listData.observe(this@MainActivity, {
                        binding.moviesRecycle.customAdapter.refresh(it)
                    })
                }
    }
}