package com.demo.demologgin.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.mvrx.viewModel
import com.demo.demologgin.MyApplication
import com.demo.demologgin.R
import com.demo.demologgin.api.RemoteDataSource
import com.demo.demologgin.databinding.ActivityMainBinding
import com.demo.demologgin.maricks.State
import com.demo.demologgin.maricks.ViewModel
import com.demo.oceantechtranning.model.TokenResponse
import com.globits.mita.data.model.UserCredentials
import com.globits.mita.data.network.AuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewModel.Factory {


    @Inject
    lateinit var viewModelFactory: ViewModel.Factory

//    val viewMosel: ViewModel by viewModel()


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).component.getMainActiviti(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



    override fun create(initialState: State): ViewModel {
        return viewModelFactory.create(initialState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}