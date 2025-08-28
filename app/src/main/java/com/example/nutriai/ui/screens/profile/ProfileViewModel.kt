package com.example.nutriai.viewmodel

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

    fun loadUser() {
        _isLoading.value = true
        viewModelScope.launch {
            userRepository.getUser { usuario ->
                _user.value = usuario
                _isLoading.value = false
            }
        }
    }

    fun saveUser(usuario: Usuario, onResult: (Boolean) -> Unit) {
        userRepository.saveUser(usuario, onResult)
    }
}
