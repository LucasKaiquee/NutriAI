package com.example.nutriai.modelo

data class Usuario(
    val nome: String,
    val idade: Int,
    val rotina: String,
    val preferenciasAlimentares: String,
    val ingredientesDisponiveis: List<Ingrediente>,
    val receitasSalvas: List<Receita>,
    val listaDeCompras: ListaDeCompras
)
