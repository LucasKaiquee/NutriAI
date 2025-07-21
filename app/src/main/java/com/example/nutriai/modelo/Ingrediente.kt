package com.example.nutriai.modelo

data class Ingrediente(
    val id: String = "",
    val nome: String = "",
    var quantidade: Float = 0f,
    val unidadeDeMedidaPadrao: String = "" // Ex: "gramas", "unidade", "ml"
)
