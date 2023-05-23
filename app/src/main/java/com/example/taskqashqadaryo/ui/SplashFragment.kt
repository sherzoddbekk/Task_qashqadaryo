package com.example.taskqashqadaryo.ui

import com.example.taskqashqadaryo.R
import com.example.taskqashqadaryo.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

//    val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreate() {
        CoroutineScope(Dispatchers.Main).launch {
//            mainViewModel.getAllCardsList()
            delay(1500)
            navController.navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

}