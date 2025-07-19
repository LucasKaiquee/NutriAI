package com.example.nutriai.modelo

data class ItemReceita(
    val ingrediente: Ingrediente,
    val quantidade: Float, // Usamos Float para quantidades como 0.5 (meia)
    val unidadeDeMedida: String // Ex: "xícara de chá", "colher de sopa"
)
