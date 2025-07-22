package com.example.nutriai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriai.modelo.Receita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReceitaViewModel : ViewModel() {

    private val _receitas = MutableStateFlow<List<Receita>>(emptyList())
    val receitas: StateFlow<List<Receita>> get() = _receitas

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        carregarReceitas()
    }

    fun carregarReceitas() {
        _isLoading.value = true
        viewModelScope.launch {
            UserRepository.getUser { user ->
                user?.let {
                    _receitas.value = it.receitas
                }
                _isLoading.value = false
            }
        }
    }
}
