package com.example.nutriai.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutriai.modelo.Receita

@Composable
fun RecipeCard(recipe: Receita) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título e Descrição
            Text(recipe.nome, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(recipe.descricao, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

            Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.primary)

            // Ingredientes
            Text("Ingredientes", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            recipe.ingredientes.forEach { item ->
                Text("• ${item.quantidade} ${item.unidadeDeMedida} de ${item.nome}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Modo de Preparo (ainda sem ação)
            Button(
                onClick = { /* Futuramente, mostrar o modo de preparo */ },
                shape = RoundedCornerShape(50) // Deixa o botão bem arredondado
            ) {
                Text("Modo de preparo")
            }

            // Categoria no canto inferior direito
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = recipe.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}