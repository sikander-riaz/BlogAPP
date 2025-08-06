package com.example.blogapp.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BlogViewModel(private val repository: BlogRepository) : ViewModel() {

    val blogs = repository.getAllBlogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun saveBlog(title: String, description: String) {
        viewModelScope.launch {
            repository.insert(Blog(title, description))
        }
    }
}

class BlogViewModelFactory(private val repository: BlogRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BlogViewModel(repository) as T
    }
}
