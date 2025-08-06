package com.example.blogapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(blog: Blog)

    @Delete
    suspend fun delete(blog: Blog)

    @Query("SELECT * FROM Blog")
    fun getAllBlogs(): Flow<List<Blog>>
}
