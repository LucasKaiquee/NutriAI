package com.example.nutriai.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutriai.modelo.Ingrediente
import com.example.nutriai.viewmodel.IngredientViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientListScreen(navController: NavController) {
    val viewModel: IngredientViewModel = koinViewModel()
    val ingredientList by viewModel.ingredients.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Ingredientes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_ingredient") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Ingrediente", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { innerPadding ->
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            ingredientList.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Você ainda não tem ingredientes.")
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ingredientList, key = { it.id }) { ingredient ->
                        Card(elevation = CardDefaults.cardElevation(2.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(ingredient.nome, fontWeight = FontWeight.Bold)
                                    Text("${ingredient.quantidade} ${ingredient.unidadeDeMedidaPadrao}")
                                }
                                IconButton(onClick = {
                                    viewModel.deleteIngredient(ingredient.id) { /* opcional: toast */ }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
