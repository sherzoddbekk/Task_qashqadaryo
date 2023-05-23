package com.example.taskqashqadaryo.ui

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.taskqashqadaryo.MainActivity
import com.example.taskqashqadaryo.databinding.FragmentCreateCardBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.taskqashqadaryo.models.Card
import com.example.taskqashqadaryo.utils.SharedPreferenceHelper
import com.example.taskqashqadaryo.utils.extension.collectLA
import com.example.taskqashqadaryo.viewmodels.CreateCardViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CreateCardFragment : BaseFragment<FragmentCreateCardBinding>(FragmentCreateCardBinding::inflate) {

    private val createCardViewModel: CreateCardViewModel by viewModels()

    @Inject
    lateinit var preferences: SharedPreferenceHelper

    override fun onViewCreate() {

        initViews()
        collectUiState()
    }

    private fun initViews() {
        binding.apply {
            ivBack.setOnClickListener {
                navController.navigateUp()
            }
            bSave.setOnClickListener {
                if (etCardNumber.text.isNotEmpty() && etCardName.text.isNotEmpty() && etCardExpdate.text.isNotEmpty()){
                    val card = Card(cardNumber = etCardNumber.text.toString(),
                        cardName = etCardName.text.toString(),
                        expireDate = etCardExpdate.text.toString()
                    )
                    if (preferences.getInternetConnection()){
                        createCardViewModel.saveToServer(card)
                    } else{
                        card.isUploaded = false
                        createCardViewModel.saveToLocal(card)
                    }
                }
            }

            etCardNumber.addTextChangedListener{
                tvCardNumber.text = etCardNumber.text.toString()
                if (etCardNumber.text.toString().isNotEmpty() && etCardNumber.text.toString().length % 4 == 0)
                    etCardNumber.text.insert(1, " ")
            }

            etCardName.addTextChangedListener {
                tvCardName.text = etCardName.text.toString()
            }

            etCardExpdate.addTextChangedListener {
                tvCardBalance.text = etCardExpdate.text.toString()
            }

        }
    }

    private fun collectUiState() {
        createCardViewModel.uiState.collectLA(viewLifecycleOwner) { uiState ->
//            binding.flLoading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
            if (uiState.card != null) {
                // set data Adapter or UI
                navController.navigateUp()

                (requireActivity() as MainActivity).mainViewModel.getAllCardsList()
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