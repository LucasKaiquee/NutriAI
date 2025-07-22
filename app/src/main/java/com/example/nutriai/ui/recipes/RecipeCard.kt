package com.example.nutriai.ui.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutriai.modelo.Receita

@Composable
fun RecipeCard(
    recipe: Receita
) {
    var modoExpandido by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nome e descrição
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

            // Botão de expandir/recolher
            Button(
                onClick = { modoExpandido = !modoExpandido },
                shape = RoundedCornerShape(50)
            ) {
                Text(if (modoExpandido) "Ocultar modo de preparo" else "Mostrar modo de preparo")
            }

            // Exibição condicional do modo de preparo
            if (modoExpandido) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Modo de preparo", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(recipe.modoDePreparo, style = MaterialTheme.typography.bodySmall)
            }

            // Categoria no rodapé
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
