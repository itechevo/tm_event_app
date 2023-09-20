package com.kupesan.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kupesan.data.model.Event

@Database(entities = [Event::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}