package com.example.blogapp.database

class BlogRepository(private val dao: BlogDao) {

    fun getAllBlogs() = dao.getAllBlogs()

    suspend fun insert(blog: Blog) = dao.upsert(blog)

    suspend fun delete(blog: Blog) = dao.delete(blog)
}
