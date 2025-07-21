package com.example.nutriai.modelo

import kotlinx.serialization.Serializable

@Serializable
data class ItemReceita(

    val nome: String = "",
    val quantidade: Float = 0f,
    val unidadeDeMedida: String = "" // Ex: "xícara de chá", "colher de sopa"
)
