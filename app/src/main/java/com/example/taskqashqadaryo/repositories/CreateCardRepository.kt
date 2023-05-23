package com.example.taskqashqadaryo.repositories

import retrofit2.HttpException
import com.example.taskqashqadaryo.local.CardDao
import com.example.taskqashqadaryo.models.Card
import com.example.taskqashqadaryo.network.services.CardService
import com.example.taskqashqadaryo.utils.NetworkResource
import com.example.taskqashqadaryo.utils.UiText
import java.io.IOException
import javax.inject.Inject

class CreateCardRepository @Inject constructor(
    private val cardService: CardService,
    private val cardDao: CardDao
) {

    suspend fun saveCardToLocal(card: Card): NetworkResource<Card> {
        return try {
            cardDao.insertCard(card)
            NetworkResource.Success(card)
        } catch (e: HttpException) {
            NetworkResource.Error(UiText.StaticString())
        } catch (e: IOException) {
            NetworkResource.Error(UiText.StaticString())
        }
    }

    suspend fun saveCardToServer(card: Card): NetworkResource<Card> {
        return try {
            val cardRequest = card.cardToCardRequest()
            val newCard = cardService.saveCard(cardRequest)
            if (newCard.isSuccessful) {
                saveCardToLocal(newCard.body()?.toCardDto()!!)
//                NetworkResource.Success(newCard.body()?.toCardDto())
            } else {
                NetworkResource.Error(UiText.StaticString())
            }
        } catch (e: HttpException) {
            NetworkResource.Error(UiText.StaticString())
        } catch (e: IOException) {
            NetworkResource.Error(UiText.StaticString())
        }
    }

}