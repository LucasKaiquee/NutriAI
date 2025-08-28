package com.example.nutriai.ui.screens.profile

import android.util.Log
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileEditScreen(navController: NavController) {
    val viewModel: ProfileViewModel = koinViewModel()
    val user by viewModel.user.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        user?.let { userData ->
            var editedUser by remember { mutableStateOf(userData) }

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
                            value = editedUser.name,
                            onValueChange = { editedUser = editedUser.copy(name = it) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Idade", color = Color(0xFF00897B))
                        OutlinedTextField(
                            value = editedUser.age.toString(),
                            onValueChange = {
                                val age = it.toIntOrNull() ?: 0
                                editedUser = editedUser.copy(age = age)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Rotina", color = Color(0xFF00897B))
                        OutlinedTextField(
                            value = editedUser.routine,
                            onValueChange = { editedUser = editedUser.copy(routine = it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            maxLines = 6
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Gostos", color = Color(0xFF43A047))
                        OutlinedTextField(
                            value = editedUser.preferences,
                            onValueChange = { editedUser = editedUser.copy(preferences = it) },
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
                        viewModel.saveUser(editedUser) { success ->
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
}
