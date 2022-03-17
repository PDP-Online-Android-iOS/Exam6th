package dev.ogabek.exam6_cards.database

import android.app.Application
import dev.ogabek.exam6_cards.helper.Logger
import dev.ogabek.exam6_cards.manager.RoomManager
import dev.ogabek.exam6_cards.model.Card

class CardRepository(application: Application) {

    private val TAG: String = CardRepository::class.java.simpleName

    private val db = RoomManager.getDatabase(application)
    private val cardDao: CardDao = db!!.userDao()

    fun getCards(): List<Card> {
        Logger.d(TAG, "${cardDao.getUsers()}")
        return cardDao.getUsers()
    }

    fun saveCard(card: Card) {
        Logger.d(TAG, "Saved")
        cardDao.saveUser(card)
    }

    fun deleteCards() {
        Logger.d(TAG, "Database cleared")
        cardDao.deleteAllUsers()
    }

}