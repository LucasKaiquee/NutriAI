package com.example.nutriai.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutriai.modelo.Usuario


@Composable
fun ProfileScreenContent(navController: NavController) {
    var user by remember { mutableStateOf<Usuario?>(null) }

    // A lógica para buscar os dados continua aqui por enquanto
    LaunchedEffect(Unit) {
        UserRepository.getUser {
            user = it
        }
    }

    // A UI começa aqui. O `innerPadding` será passado pelo Scaffold principal.
    user?.let { userData ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // O padding do Scaffold já foi aplicado antes
                .verticalScroll(rememberScrollState())
        ) {
            // Todo o seu código de Surface, Text, Box, Button, etc., continua aqui...
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF9F9F9)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Informações pessoais", fontSize = 18.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Nome", color = Color(0xFF00897B), fontSize = 14.sp)
                    Text(userData.name, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Idade", color = Color(0xFF00897B), fontSize = 14.sp)
                    Text(userData.age.toString(), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Rotina", color = Color(0xFF00897B), fontSize = 14.sp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(userData.routine, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Gostos", color = Color(0xFF43A047), fontSize = 14.sp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(userData.preferences, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate("editProfile") },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Editar")
                    }
                }
            }
        }
    } ?: run {
        // A tela de carregamento também fica aqui
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Carregando perfil...")
        }
    }
}


