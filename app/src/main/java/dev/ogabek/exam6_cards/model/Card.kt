package dev.ogabek.exam6_cards.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cards")
data class Card(
    val balance: String,
    val card_holder: String,
    val card_number: String,
    val card_type: String,
    val cvv: String,
    val expires: String,

    @PrimaryKey
    val id: String,
): Serializable