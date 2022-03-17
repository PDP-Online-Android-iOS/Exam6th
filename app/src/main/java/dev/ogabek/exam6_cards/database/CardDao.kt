package dev.ogabek.exam6_cards.database

import androidx.room.*
import dev.ogabek.exam6_cards.model.Card

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(card: Card)

    @Query("SELECT * FROM cards")
    fun getUsers(): List<Card>

    @Query("DELETE FROM cards")
    fun deleteAllUsers()

}