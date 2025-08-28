package com.example.nutriai.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriai.data.UserRepository
import com.example.nutriai.data.GeradorReceitas
import com.example.nutriai.modelo.Receita
import com.example.nutriai.ui.recipes.JSONParser
import com.example.nutriai.ui.recipes.ParseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RecipeUiState {
    data object Idle : RecipeUiState
    data object Loading : RecipeUiState
    data class Success(val recipe: Receita) : RecipeUiState
    data class Error(val message: String) : RecipeUiState
}

class GenerateRecipeViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun generateRecipeFor(mealType: String) {
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading

            userRepository.getUser { user ->
                if (user == null) {
                    _uiState.value = RecipeUiState.Error("Não foi possível carregar os dados do usuário.")
                    return@getUser
                }

                val ingredientNames = user.ingredientes.map { it.formatar() }

                println("-------Lista ingredientes------------")
                println(ingredientNames.toString())

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

                    when (val parseResult = JSONParser.parse(cleanedJsonString)) {
                        is ParseResult.Success -> {
                            val recipeObject = parseResult.recipe
                            userRepository.addGeneratedRecipe(recipeObject) { success ->
                                if (success) {
                                    _uiState.value = RecipeUiState.Success(recipeObject)
                                } else {
                                    _uiState.value = RecipeUiState.Error("Falha ao salvar a receita gerada.")
                                }
                            }
                        }
                        is ParseResult.Error -> {
                            _uiState.value = RecipeUiState.Error("Erro de Parse: ${parseResult.exception.message}")
                        }
                    }
                }
            }
        }
    }
}
