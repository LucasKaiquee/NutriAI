package com.example.nutriai.ui.screens.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutriai.modelo.Usuario

@Composable
fun ProfileEditScreen(navController: NavController) {
    var user by remember { mutableStateOf(Usuario()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        UserRepository.getUser {
            if (it != null) user = it
            isLoading = false
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Editar Perfil",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(Color.White),
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Informações pessoais", fontSize = 18.sp, color = Color.Black)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Nome", color = Color(0xFF00897B))
                    OutlinedTextField(
                        value = user.name,
                        onValueChange = { user = user.copy(name = it) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Idade", color = Color(0xFF00897B))
                    OutlinedTextField(
                        value = user.age.toString(),
                        onValueChange = {
                            val age = it.toIntOrNull() ?: 0
                            user = user.copy(age = age)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Rotina", color = Color(0xFF00897B))
                    OutlinedTextField(
                        value = user.routine,
                        onValueChange = { user = user.copy(routine = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 6
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Gostos", color = Color(0xFF43A047))
                    OutlinedTextField(
                        value = user.preferences,
                        onValueChange = { user = user.copy(preferences = it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        maxLines = 8
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    UserRepository.saveUser(user) { success ->
                        if (success) {
                            navController.navigate("perfil") {
                                popUpTo("editProfile") { inclusive = true }
                            }
                        } else {
                            Log.e("ProfileEdit", "Erro ao salvar usuário")

                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Salvar")
            }
        }
    }
}
