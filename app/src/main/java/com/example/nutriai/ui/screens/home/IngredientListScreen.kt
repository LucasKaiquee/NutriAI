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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientListScreen(navController: NavController) {
    var ingredientList by remember { mutableStateOf<List<Ingrediente>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Função para buscar/atualizar a lista de ingredientes
    fun fetchIngredients() {
        isLoading = true
        UserRepository.getUser { user ->
            ingredientList = user?.ingredientes ?: emptyList()
            isLoading = false
        }
    }

    // Busca os dados quando a tela é aberta pela primeira vez
    LaunchedEffect(key1 = Unit) {
        fetchIngredients()
    }

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
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (ingredientList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Você ainda não tem ingredientes.")
            }
        } else {
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
                                // Deleta o ingrediente e atualiza a lista na UI
                                UserRepository.deleteIngredient(ingredient.id) { success ->
                                    if (success) {
                                        fetchIngredients() // Recarrega a lista
                                    }
                                }
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