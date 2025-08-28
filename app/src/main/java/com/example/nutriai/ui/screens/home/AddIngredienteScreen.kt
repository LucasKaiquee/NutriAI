package com.example.nutriai.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutriai.modelo.Ingrediente
import com.example.nutriai.viewmodel.IngredientViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientScreen(
    navController: NavController
) {
    val viewModel: IngredientViewModel = koinViewModel()
    val isLoading by viewModel.isLoading.collectAsState()

    var nome by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var unidade by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Ingrediente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar")
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do ingrediente") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = quantidade,
                onValueChange = { quantidade = it.filter { c -> c.isDigit() || c == '.' } },
                label = { Text("Quantidade") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = unidade,
                onValueChange = { unidade = it },
                label = { Text("Unidade de medida (g, kg, ml, L, unidade)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (nome.isNotBlank() && quantidade.isNotBlank()) {
                            val novoIngrediente = Ingrediente(
                                nome = nome.trim(),
                                quantidade = quantidade.toFloatOrNull() ?: 0f,
                                unidadeDeMedidaPadrao = unidade.trim()
                            )

                            viewModel.addIngredient(novoIngrediente) { success ->
                                if (success) {
                                    Toast.makeText(context, "Ingrediente salvo com sucesso!", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Falha ao salvar o ingrediente.", Toast.LENGTH_SHORT).show()
                                }
                            }

                        } else {
                            Toast.makeText(context, "Preencha pelo menos o nome e a quantidade.", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Salvar Ingrediente")
                }
            }
        }
    }
}