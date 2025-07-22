package com.example.nutriai.ui.screens.lists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutriai.ui.recipes.RecipeCard
import com.example.nutriai.viewmodel.ReceitaViewModel

@Composable
fun RecipeListScreen(
    receitaViewModel: ReceitaViewModel = viewModel()
) {
    val receitas by receitaViewModel.receitas.collectAsState()
    val isLoading by receitaViewModel.isLoading.collectAsState()

    Scaffold(
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(receitas) { receita ->
                    RecipeCard(recipe = receita)
                }
            }
        }
    }
}
