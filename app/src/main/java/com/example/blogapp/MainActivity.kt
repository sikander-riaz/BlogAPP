package com.example.blogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blogapp.ui.theme.BlogAPPTheme

// Keep your existing BlogPost data class
data class BlogPost(val title: String, val description: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlogAPPTheme {
                BlogNavigation()
            }
        }
    }
}

@Composable
fun BlogNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "blog_list"
    ) {
        composable("blog_list") {
            BlogScreen(
                onNavigateToDetail = { blogPost ->
                    // Navigate to detail screen, encoding the data
                    navController.navigate("blog_detail/${blogPost.title}/${blogPost.description}")
                }
            )
        }

        composable("blog_detail/{title}/{description}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""

            BlogDetailScreen(
                title = title,
                description = description,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(
    onNavigateToDetail: ((BlogPost) -> Unit)? = null
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val blogPosts = remember { mutableStateListOf<BlogPost>() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Blog APP") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Title TextField
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                placeholder = { Text("Book recipe") },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Description TextField
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Enter your description") },
                placeholder = { Text("Apple keeps docs away") },
                minLines = 3,
                maxLines = 5,
                 colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Red,
                    unfocusedTextColor = Color.Blue,
                    focusedContainerColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Save Button
            Button(
                onClick = {
                    val trimmedTitle = title.trim()
                    val trimmedDesc = description.trim()
                    if (trimmedTitle.isNotEmpty() && trimmedDesc.isNotEmpty()) {
                        blogPosts.add(BlogPost(trimmedTitle, trimmedDesc))
                        title = ""
                        description = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Blog Posts List
            if (blogPosts.isNotEmpty()) {
                Text(
                    text = "Your Blog Posts (Tap to view details)",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            LazyColumn {
                items(blogPosts) { post ->
                    BlogPostCard(
                        post = post,
                        onClick = {
                            onNavigateToDetail?.invoke(post)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BlogPostCard(
    post: BlogPost,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tap to read more...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    title: String,
    description: String,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Blog Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                }
            }
        }
    }
}