package com.example.nutriai.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutriai.components.ServicesSection

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

        ServicesSection(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        Button(onClick = { navController.navigate("generate_recipe") }) {
            Text("Gerar Receita com IA")
        }


    }
}