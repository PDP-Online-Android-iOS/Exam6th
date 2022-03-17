package dev.ogabek.exam6_cards.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import dev.ogabek.exam6_cards.R
import dev.ogabek.exam6_cards.model.Card
import kotlin.math.exp

class CreateCardActivity : AppCompatActivity() {

    private lateinit var iv_close: ImageView

    private lateinit var et_card_number: TextInputEditText

    private lateinit var et_expires_month: TextInputEditText
    private lateinit var et_expires_year: TextInputEditText

    private lateinit var et_svv: TextInputEditText

    private lateinit var et_holder_name: TextInputEditText

    private lateinit var btn_add: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        initViews()

    }

    private fun initViews() {
        iv_close = findViewById(R.id.iv_close)
        et_card_number = findViewById(R.id.card_number)
        et_expires_month = findViewById(R.id.month)
        et_expires_year = findViewById(R.id.year)
        et_svv = findViewById(R.id.svv)
        et_holder_name = findViewById(R.id.name)
        btn_add = findViewById(R.id.btn_add)
        onClicks()
    }

    private fun onClicks() {

        iv_close.setOnClickListener {
            finish()
        }

        val card_number = findViewById<TextView>(R.id.tv_number)

        et_card_number.addTextChangedListener {
            val text = if (it.isNullOrEmpty()) "" else it.toString()
            if (text != "") {
                if (text.last().isDigit()) {
                    when {
                        text.length > 12 -> {
                            val forText = "${text.substring(0, 4)}   ${text.substring(4, 8)}   ${text.substring(8, 12)}   ${text.substring(12)}"
                            card_number.text = forText
                        }
                        text.length > 8 -> {
                            val forText = "${text.substring(0, 4)}   ${text.substring(4, 8)}   ${text.substring(8)}"
                            card_number.text = forText
                        }
                        text.length > 4 -> {
                            val forText = "${text.substring(0, 4)}   ${text.substring(4)}"
                            card_number.text = forText
                        }
                        else -> {
                            card_number.text = it.toString()
                        }
                    }
                } else {
                    et_card_number.error = "Enter valid number"
                }
            } else card_number.text = ""
        }

        et_svv.addTextChangedListener {
            val text = if (it.isNullOrEmpty()) "" else it.toString()
            if (text != "") {
                if (text.length == 3) {
                    if (!text.last().isDigit()) {
                        et_svv.error = "Enter valid number"
                    }
                } else {
                    et_svv.error = "Enter valid number"
                }
            }
        }

        val name = findViewById<TextView>(R.id.tv_card_holder)

        et_holder_name.addTextChangedListener {
            val text = if (it.isNullOrEmpty()) "" else it.toString()
            if (text != "") {
                if (text.last().isLetter()) {
                    name.text = text
                } else {
                    et_holder_name.error = "Enter valid name"
                }
            } else name.text = ""
        }

        var month = ""
        var year = ""
        val expires = findViewById<TextView>(R.id.tv_expires)

        et_expires_month.addTextChangedListener {
            val text = if (it.isNullOrEmpty()) "" else it.toString()
            if (text != "") {
                if (!text.last().isDigit() || text.toInt() > 12 || text.toInt() < 1) {
                    et_expires_month.error = "Enter valid month number"
                } else {
                    month = text
                    expires.text = "$month / $year"
                }
            } else expires.text = ""
        }

        et_expires_year.addTextChangedListener {
            val text = if (it.isNullOrEmpty()) "" else it.toString()
            if (text != "") {
                if (!text.last().isDigit() || text.toInt() < 22) {
                    et_expires_year.error = "Enter valid month number"
                } else {
                    year = text
                    expires.text = "$month / $year"
                }
            } else expires.text = ""
        }

        val card_type = "visa"

        btn_add.setOnClickListener {
            val card: Card = Card("", name.text.toString(), card_number.text.toString(), card_type, et_svv.text.toString(), expires.text.toString(), "")
            val intent = Intent()
            intent.putExtra("result", card)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

}