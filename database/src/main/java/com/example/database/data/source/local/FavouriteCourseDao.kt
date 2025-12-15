package com.example.database.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.data.FavouriteCourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCourseDao {
    @Query("SELECT * FROM favourite_courses")
    fun getAllAsFlow(): Flow<List<FavouriteCourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favouriteCourseEntity: FavouriteCourseEntity)

    @Query("DELETE FROM favourite_courses WHERE id=:id")
    fun deleteById(id: Int)
}