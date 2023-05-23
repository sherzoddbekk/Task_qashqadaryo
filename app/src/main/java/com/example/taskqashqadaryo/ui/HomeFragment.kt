package com.example.taskqashqadaryo.ui

import android.util.Log
import com.example.taskqashqadaryo.MainActivity
import com.example.taskqashqadaryo.R
import com.example.taskqashqadaryo.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.taskqashqadaryo.adapter.CardsAdapter
import com.example.taskqashqadaryo.utils.extension.collectLA

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val cardsAdapter by lazy { CardsAdapter() }

    override fun onViewCreate() {
        binding.ivSaveCard.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_createCardFragment)
        }
        binding.rvCards.adapter = cardsAdapter

        collectUiState()
    }


    private fun collectUiState() {
        (requireActivity() as MainActivity).mainViewModel.uiState.collectLA(viewLifecycleOwner) { uiState ->
//            binding.flLoading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            if (uiState.cardList.isNotEmpty()) {
                // set data Adapter or UI
                cardsAdapter.submitList(uiState.cardList)
            }

            if (uiState.isLoading) {
                val loaderDialog = true  // loaderDialog show
            } else {
                val loaderDialog = false  // loaderDialog dismiss
            }

            Log.d("RRR", "Error: ${uiState.errorMessage}")
        }
    }
}