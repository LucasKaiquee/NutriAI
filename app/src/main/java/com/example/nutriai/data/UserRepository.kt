// app/src/main/java/com/example/nutriai/data/UserRepository.kt

package com.example.nutriai.data

import android.util.Log
import com.example.nutriai.data.local.UserDAO
import com.example.nutriai.modelo.Ingrediente
import com.example.nutriai.modelo.Receita
import com.example.nutriai.modelo.Usuario
import kotlinx.coroutines.flow.Flow // Importe o Flow
import java.util.UUID

class UserRepository(
    private val userDao: UserDAO
) {
    private val userId = "FfyTYvx2zqWZSCi4Sg04DDK62B93"

    // Nova função para observar o usuário
    fun getObservableUser(): Flow<Usuario?> {
        return userDao.getObservableUser(userId)
    }

    suspend fun getUser(): Usuario? {
        return userDao.getUser(userId)
    }

    // ... (o restante do arquivo continua igual)
    suspend fun saveUser(user: Usuario) {
        userDao.saveUser(user)
    }

    suspend fun addIngredient(newIngredient: Ingrediente): Boolean {
        return try {
            val currentUser = getUser()
            if (currentUser != null) {
                val updatedIngredients = currentUser.ingredientes.toMutableList()
                val ingredientWithId = newIngredient.copy(id = UUID.randomUUID().toString())
                updatedIngredients.add(ingredientWithId)

                val updatedUser = currentUser.copy(ingredientes = updatedIngredients)
                saveUser(updatedUser)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Erro ao adicionar ingrediente", e)
            false
        }
    }

    suspend fun deleteIngredient(ingredientId: String): Boolean {
        return try {
            val currentUser = getUser()
            if (currentUser != null) {
                val updatedIngredients = currentUser.ingredientes.filterNot { it.id == ingredientId }
                val updatedUser = currentUser.copy(ingredientes = updatedIngredients)
                saveUser(updatedUser)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Erro ao deletar ingrediente", e)
            false
        }
    }

    suspend fun addGeneratedRecipe(newRecipe: Receita): Boolean {
        return try {
            val currentUser = getUser()
            if (currentUser != null) {
                val updatedRecipes = currentUser.receitas.toMutableList()
                val recipeWithId = newRecipe.copy(id = UUID.randomUUID().toString())
                updatedRecipes.add(recipeWithId)

                val updatedUser = currentUser.copy(receitas = updatedRecipes)
                saveUser(updatedUser)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Erro ao adicionar receita", e)
            false
        }
    }
}