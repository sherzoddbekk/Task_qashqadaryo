package com.example.taskqashqadaryo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskqashqadaryo.models.Card
import com.example.taskqashqadaryo.utils.Constants

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCardDao(): CardDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {

            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.CARDS_DATABASE
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

            return instance!!
        }
    }
}
