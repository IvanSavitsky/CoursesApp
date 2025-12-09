package com.example.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_courses")
data class FavouriteCourseEntity(
    @PrimaryKey()
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)