package com.example.nutriai.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutriai.components.ServicesSection
import com.example.nutriai.ui.recipe.GenerateRecipeViewModel
import com.example.nutriai.ui.recipes.RecipeCard
import com.example.nutriai.ui.recipe.RecipeUiState
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

fun getCurrentMealTime(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..10 -> "Café da manhã"
        in 11..13 -> "Almoço"
        in 14..17 -> "Lanche"
        else -> "Jantar"
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GenerateRecipeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentMeal = remember { getCurrentMealTime() }

    // Usamos um rememberSaveable para preservar o estado mesmo se recompor
    val recipeRequested = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!recipeRequested.value) {
            viewModel.generateRecipeFor(currentMeal)
            recipeRequested.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ServicesSection(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text("Sugestão para $currentMeal", style = MaterialTheme.typography.titleMedium)

        when (val state = uiState) {
            is RecipeUiState.Idle, is RecipeUiState.Loading -> {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
            is RecipeUiState.Error -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Erro ao gerar receita: ${state.message}")
            }
            is RecipeUiState.Success -> {
                Spacer(modifier = Modifier.height(16.dp))
                RecipeCard(recipe = state.recipe)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Permite gerar nova receita ao navegar para outra tela
                recipeRequested.value = false
                navController.navigate("generate_recipe")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gerar nova receita com IA")
        }
    }
}