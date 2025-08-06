package com.example.blogapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Blog(
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
