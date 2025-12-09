package com.example.database

import androidx.room.Room
import com.example.database.data.source.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "favourite_courses.db"
        ).build()
    }
    single {
        val db: AppDatabase = get()
        db.favouriteCourseDao()
    }
}