package dev.ogabek.exam6_cards.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.exam6_cards.R
import dev.ogabek.exam6_cards.model.Card


class CardAdapter(private val context: Context, private val cards: ArrayList<Card>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)

        return CardViewHolder(view)
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: CardView = view.findViewById(R.id.item)
        val card: LinearLayout = view.findViewById(R.id.item_card)
        val type: ImageView = view.findViewById(R.id.iv_type)
        val balance: TextView = view.findViewById(R.id.tv_balance)
        val number: TextView = view.findViewById(R.id.tv_number)
        val card_holder: TextView = view.findViewById(R.id.tv_card_holder)
        val expires: TextView = view.findViewById(R.id.tv_expires)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val card = cards[position]

        if (holder is CardViewHolder) {
            holder.apply {
                item.setCardBackgroundColor(Color.parseColor("#3C4A81"))
                if (card.card_type.lowercase() == "visa") {
                    type.setImageResource(R.drawable.visa)
                } else {
                    type.setImageResource(R.drawable.mastercard)
                }
                balance.text = "Balance  $${card.balance}"
                number.text = card.card_number
                card_holder.text = card.card_holder
                expires.text = card.expires
            }
        }

    }

    override fun getItemCount() = cards.size
}