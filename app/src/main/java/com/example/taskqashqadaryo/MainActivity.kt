package com.example.taskqashqadaryo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.taskqashqadaryo.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.taskqashqadaryo.utils.NetworkConnectionListener
import com.example.taskqashqadaryo.utils.SharedPreferenceHelper
import com.example.taskqashqadaryo.utils.extension.collectLatestLA
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val networkListener by lazy { NetworkConnectionListener() }
    @Inject
    lateinit var preferences: SharedPreferenceHelper
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){

        networkListener.checkNetworkAvailability(this)
            .collectLatestLA(this) { status ->
                preferences.setInternetConnection(status)
//                Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
                when (status) {
                    true -> {
                        mainViewModel.refreshAllCardsList()
                    }
                    false -> {
                        mainViewModel.getAllCardsList()
                    }
                }
            }
    }
}