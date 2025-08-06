package com.example.blogapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Blog::class], version = 1)
abstract class BlogDatabase : RoomDatabase() {
    abstract fun blogDao(): BlogDao
}
