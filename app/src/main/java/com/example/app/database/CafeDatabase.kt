package com.example.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uklkasir.userdatabase.CafeDao

@Database(
    entities = [
        DetailTransaksi::class,
    Meja::class,
    Menu::class,
    User::class,
    Transaksi::class
    ],
    version = 2
)
abstract class CafeDatabase: RoomDatabase() {
    abstract fun cafeDao(): CafeDao

    companion object{
        private var instance: CafeDatabase? = null

        fun getInstance(context: Context): CafeDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(context, CafeDatabase::class.java, "user_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }
}