package com.example.mydemoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mydemoproject.base.MyViewModelFactory
import com.example.mydemoproject.databinding.ActivityMainBinding
import com.example.mydemoproject.data.remote.ApiHelper
import com.example.mydemoproject.data.remote.RetrofitBuilder
import com.example.mydemoproject.ui.view.MoviesRecycleView
import com.example.mydemoproject.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpViewModel()
        setUpRecycleView()
    }

    fun setUpViewModel() {
        viewModel =
            ViewModelProviders.of(this, MyViewModelFactory(ApiHelper(RetrofitBuilder().apiService)))
                .get(MainViewModel::class.java).apply {
                    getDataMovies()
                    listData.observe(this@MainActivity, {
                        binding.moviesRecycle.customAdapter.refresh(it)
                        binding.progressLoading.visibility = View.GONE
                    })
                    listInsert.observe(this@MainActivity, {
                        binding.moviesRecycle.customAdapter.add(it)
                        binding.progressLoading.visibility = View.GONE
                    })
                    isLastPage.observe(this@MainActivity, {
                        binding.moviesRecycle.customAdapter.hasNext = !it
                    })
                }
    }

    fun setUpRecycleView() {
        binding.moviesRecycle.customAdapter.callBack =
            object : MoviesRecycleView.Adapter.moviesAdapterCallback {
                override fun onNextClick() {
                    binding.progressLoading.visibility = View.VISIBLE
                    viewModel.getDataMovies(true)
                }

            }
    }
}