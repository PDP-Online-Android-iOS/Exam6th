package dev.ogabek.exam6_cards.activity

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsetsController
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.exam6_cards.R
import dev.ogabek.exam6_cards.adapter.CardAdapter
import dev.ogabek.exam6_cards.database.CardRepository
import dev.ogabek.exam6_cards.helper.Logger
import dev.ogabek.exam6_cards.model.Card
import dev.ogabek.exam6_cards.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var iv_add: ImageView
    private lateinit var rv_cards: RecyclerView

    private lateinit var cards: ArrayList<Card>

    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private var resultActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data:Intent? = result.data
            val result = data!!.getSerializableExtra("result")
            if (isInternetAvailable()) {
                saveCard(result as Card)
            } else {
                saveDatabase(result as Card)
            }
        }
    }

    private fun saveCard(card: Card) {
        RetrofitHttp.cardService.createCard(card).enqueue(object: Callback<Card> {
            override fun onResponse(call: Call<Card>, response: Response<Card>) {
                if (response.body() != null) {
                    Logger.d(TAG, response.body().toString())

                    getCards()

                } else {
                    Logger.e(TAG, "null")
                }
            }

            override fun onFailure(call: Call<Card>, t: Throwable) {
                Logger.e(TAG, t.localizedMessage)
            }

        })
    }

    private fun initViews() {
        iv_add = findViewById(R.id.iv_add)
        iv_add.setOnClickListener {
            val intent = Intent(this, CreateCardActivity::class.java)
            resultActivity.launch(intent)
        }

        cards = ArrayList()

        rv_cards = findViewById(R.id.rv_cards)
        rv_cards.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)

        rv_cards.adapter = CardAdapter(this, cards)

        if (isInternetAvailable()) {
            getCards()
        } else {
            getCardsFromDatabase()
        }

    }

    private fun getCardsFromDatabase() {
        val repository = CardRepository(application)
        Logger.d(TAG, repository.getCards().toString())
        cards.clear()
        cards.addAll(repository.getCards())
        rv_cards.adapter!!.notifyDataSetChanged()
    }

    private fun getCards() {
        RetrofitHttp.cardService.getAllCards().enqueue(object: Callback<ArrayList<Card>>{
            override fun onResponse(call: Call<ArrayList<Card>>, response: Response<ArrayList<Card>>) {
                if (response.body() != null) {
                    Logger.d(TAG, response.body().toString())
                    cards.clear()
                    cards.addAll(response.body()!!)
                    rv_cards.adapter!!.notifyDataSetChanged()

                    saveToDatabase(response.body()!!)

                } else {
                    Logger.e(TAG, "null")
                }
            }

            override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                Logger.e(TAG, t.localizedMessage)
            }

        })
    }

    private fun saveToDatabase(cards: ArrayList<Card>) {
        val repository = CardRepository(application)
        for (i in cards) repository.saveCard(i)
        Logger.d(TAG, "Cards saved to database")
        getCardsFromDatabase()
    }

    private fun saveDatabase(card: Card) {
        val repository = CardRepository(application)
        repository.saveCard(card)
        Logger.d(TAG, "Cards saved to database")
    }

    fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }

}
