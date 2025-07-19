package com.example.nutriai.modelo

data class Receita(
    val nome: String,
    val descricao: String,
    val categoria: String, // Ex: "Café da manhã"
    val ingredientes: List<ItemReceita>, // Uma lista de itens da receita
    val modoDePreparo: String
)
