package dev.ogabek.exam6_cards.manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ogabek.exam6_cards.model.Card
import dev.ogabek.exam6_cards.database.CardDao

@Database(entities = [Card::class], version = 2)
abstract class RoomManager : RoomDatabase() {

    abstract fun userDao(): CardDao

    companion object {

        private var INSTANCE: RoomManager? = null

        fun getDatabase(context: Context): RoomManager? {
            if (INSTANCE == null) {
                synchronized(RoomManager::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoomManager::class.java, "cards.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

    }

}