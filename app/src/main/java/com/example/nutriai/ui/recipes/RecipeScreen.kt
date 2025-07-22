package com.example.nutriai.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nutriai.ui.recipe.GenerateRecipeViewModel
import com.example.nutriai.ui.recipe.RecipeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateRecipeScreen(
    navController: NavController,
    viewModel: GenerateRecipeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedMeal by remember { mutableStateOf<String?>(null) }
    val mealOptions = listOf("Café da manhã", "Almoço", "Lanche", "Jantar")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerar Nova Receita") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Para qual refeição você quer uma sugestão?", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Chips para seleção da refeição
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                mealOptions.forEach { meal ->
                    FilterChip(
                        selected = meal == selectedMeal,
                        onClick = {
                            selectedMeal = meal
                            viewModel.generateRecipeFor(meal)
                        },
                        label = { Text(meal) }
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))

            // Área de Resultado
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (val state = uiState) {
                    is RecipeUiState.Idle -> {
                        Text("Selecione um tipo de refeição acima.")
                    }
                    is RecipeUiState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is RecipeUiState.Error -> {
                        Text("Erro: ${state.message}")
                    }
                    is RecipeUiState.Success -> {
                        // Agora o estado de sucesso tem o objeto, que passamos para o Card
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            RecipeCard(recipe = state.recipe)
                        }
                    }
                }
            }
        }
    }
}