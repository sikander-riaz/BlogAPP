package com.example.blogapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blogapp.ui.theme.BlogAPPTheme
import androidx.compose.ui.graphics.Color

// Step 1: Define data class
data class BlogPost(val title: String, val description: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            BlogAPPTheme {
                BlogScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen() {
    // Step 2: State for input and blog list
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val blogPosts = remember { mutableStateListOf<BlogPost>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My blog",
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Blog Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Save Button
            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank()) {
                        blogPosts.add(BlogPost(title, description))
                        title = ""
                        description = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().background(Color.LightGray)
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Blog List
            Text("Saved Blogs:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(blogPosts) { post ->
                    BlogPostCard(post)
                }
            }
        }
    }
}

@Composable
fun BlogPostCard(post: BlogPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    BlogAPPTheme {
        BlogScreen()
    }
}
