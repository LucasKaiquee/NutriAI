package com.example.nutriai.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriai.data.UserRepository
import com.example.nutriai.modelo.Ingrediente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // O StateFlow agora é criado a partir do Flow do repositório.
    // Ele vai se atualizar sozinho!
    val ingredients: StateFlow<List<Ingrediente>> =
        userRepository.getObservableUser()
            .map { user -> user?.ingredientes ?: emptyList() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Não precisamos mais do isLoading para a lista principal,
    // pois o Flow gerencia os estados.
    // Manteremos um para as ações de adicionar/deletar.
    private val _isActing = MutableStateFlow(false)
    val isActing: StateFlow<Boolean> get() = _isActing


    // O init e a função loadIngredients() não são mais necessários para popular a lista.

    // Adicionar ingrediente
    fun addIngredient(ingrediente: Ingrediente, onResult: (Boolean) -> Unit) {
        _isActing.value = true
        viewModelScope.launch {
            val success = userRepository.addIngredient(ingrediente)
            // Não precisamos mais chamar loadIngredients(). O Flow faz isso por nós.
            _isActing.value = false
            onResult(success)
        }
    }

    // Deletar ingrediente
    fun deleteIngredient(id: String, onResult: (Boolean) -> Unit) {
        _isActing.value = true
        viewModelScope.launch {
            val success = userRepository.deleteIngredient(id)
            // Não precisamos mais chamar loadIngredients(). O Flow faz isso por nós.
            _isActing.value = false
            onResult(success)
        }
    }
}