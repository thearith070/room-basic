package com.thearith.room

import android.app.Application
import androidx.room.Room
import com.thearith.room.db.AppDatabase

class AppApplication : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "room.db"
        ).allowMainThreadQueries()
            .build()
    }
}