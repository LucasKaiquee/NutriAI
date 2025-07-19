package com.example.nutriai.modelo

data class Usuario(
    val uid: String = "",
    val nome: String = "",
    val email: String = "",
    val idade: Int? = null,
    val rotina: String = "",
    val preferenciasAlimentares: String = ""
)
