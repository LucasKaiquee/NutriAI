package com.example.nutriai.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutriai.data.UserRepository
import com.example.nutriai.modelo.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<Usuario?>(null)
    val user: StateFlow<Usuario?> get() = _user

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        loadUser()
    }

    private fun loadUser() {
        _isLoading.value = true
        viewModelScope.launch {
            _user.value = userRepository.getUser()
            _isLoading.value = false
        }
    }

    fun saveUser(usuario: Usuario, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                userRepository.saveUser(usuario)
                onResult(true)
            } catch (e: Exception) {
                // O ideal seria logar o erro aqui
                onResult(false)
            }
        }
    }
}