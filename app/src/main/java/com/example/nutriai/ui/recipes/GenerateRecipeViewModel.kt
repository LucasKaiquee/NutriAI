package com.example.nutriai.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriai.data.GeradorReceitas
import com.example.nutriai.modelo.Receita
import com.example.nutriai.ui.recipes.JSONParser
import com.example.nutriai.ui.recipes.ParseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sealed Interface para representar os estados da nossa UI de forma segura
sealed interface RecipeUiState {
    data object Idle : RecipeUiState // Estado inicial
    data object Loading : RecipeUiState // Carregando a resposta da IA
    data class Success(val recipe: Receita) : RecipeUiState
    data class Error(val message: String) : RecipeUiState // Erro
}

class GenerateRecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Idle)
    val uiState = _uiState.asStateFlow()

    // Em GenerateRecipeViewModel.kt

    fun generateRecipeFor(mealType: String) {
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading

            UserRepository.getUser { user ->
                if (user == null) {
                    _uiState.value = RecipeUiState.Error("Não foi possível carregar os dados do usuário.")
                    return@getUser
                }

                val ingredientNames = user.ingredientes.map { it.nome }

                viewModelScope.launch {
                    val rawResponse = GeradorReceitas.generateRecipe(
                        rotina = "Para a refeição: $mealType",
                        gostos = user.preferences,
                        ingredientes = ingredientNames
                    )

                    val cleanedJsonString = rawResponse
                        .replace("```json", "")
                        .replace("```", "")
                        .trim()

                    // A ViewModel agora verifica o tipo de resultado do parser
                    when (val parseResult = JSONParser.parse(cleanedJsonString)) {
                        is ParseResult.Success -> {
                            // O parse deu certo, continua o fluxo de salvar
                            val recipeObject = parseResult.recipe
                            UserRepository.addGeneratedRecipe(recipeObject) { success ->
                                if (success) {
                                    _uiState.value = RecipeUiState.Success(recipeObject)
                                } else {
                                    _uiState.value = RecipeUiState.Error("Falha ao salvar a receita gerada.")
                                }
                            }
                        }
                        is ParseResult.Error -> {
                            // O parse falhou! Mostramos a mensagem de erro detalhada.
                            _uiState.value = RecipeUiState.Error("Erro de Parse: ${parseResult.exception.message}")
                        }
                    }
                }
            }
        }
    }
}