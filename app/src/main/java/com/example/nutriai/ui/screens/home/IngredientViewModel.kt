package com.example.nutriai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriai.data.UserRepository
import com.example.nutriai.modelo.Ingrediente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // Lista de ingredientes
    private val _ingredients = MutableStateFlow<List<Ingrediente>>(emptyList())
    val ingredients: StateFlow<List<Ingrediente>> get() = _ingredients

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        loadIngredients()
    }

    // Carregar ingredientes do usuário
    fun loadIngredients() {
        _isLoading.value = true
        viewModelScope.launch {
            userRepository.getUser { user ->
                _ingredients.value = user?.ingredientes ?: emptyList()
                _isLoading.value = false
            }
        }
    }

    // Adicionar ingrediente
    fun addIngredient(ingrediente: Ingrediente, onResult: (Boolean) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            userRepository.addIngredient(ingrediente) { success ->
                if (success) loadIngredients()
                _isLoading.value = false
                onResult(success)
            }
        }
    }

    // Deletar ingrediente
    fun deleteIngredient(id: String, onResult: (Boolean) -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            userRepository.deleteIngredient(id) { success ->
                if (success) loadIngredients()
                _isLoading.value = false
                onResult(success)
            }
        }
    }
}
