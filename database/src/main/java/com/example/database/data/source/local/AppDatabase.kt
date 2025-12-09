package com.example.database.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.data.FavouriteCourseEntity

@Database(entities = [FavouriteCourseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteCourseDao(): FavourtiteCourseDao
}