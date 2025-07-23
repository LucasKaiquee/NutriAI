package com.example.nutriai.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutriai.R

@Composable
fun ServicesSection(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Serviços",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ServiceItem(
                icon = {
                    Icon(
                        // Carrega seu XML da pasta drawable
                        painter = painterResource(id = R.drawable.ic_coffee),
                        contentDescription = "Nova Receita",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.requiredSize(36.dp)
                    )
                },
                label = "Nova Receita",
                onClick = { navController.navigate("generate_recipe")}
            )
            ServiceItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Novo ingrediente",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = "Novo ingrediente",
            onClick = { navController.navigate("add_ingredient") }
            )
            ServiceItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Lista de ingredientes",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = "Ingredientes",
                onClick = { navController.navigate("ingredientes") }
            )
        }
    }
}