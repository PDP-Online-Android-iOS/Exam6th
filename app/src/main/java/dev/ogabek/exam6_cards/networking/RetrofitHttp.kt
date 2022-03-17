package dev.ogabek.exam6_cards.networking

import dev.ogabek.exam6_cards.networking.service.CardService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHttp {

    companion object {

        private val TAG: String = RetrofitHttp::class.java.simpleName

        const val IS_TESTER: Boolean = true

        private const val SERVER_DEVELOPMENT = "https://62220b53666291106a1b35d3.mockapi.io/"
        private const val SERVER_PRODUCTION = "https://62220b53666291106a1b35d3.mockapi.io/"

        private fun server(): String {
            return if (IS_TESTER) {
                SERVER_DEVELOPMENT
            } else {
                SERVER_PRODUCTION
            }
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(server())
                .build()
        }

        val cardService: CardService = getRetrofit().create(CardService::class.java)

    }

}