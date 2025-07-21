package com.example.nutriai.modelo

import kotlinx.serialization.Serializable

@Serializable
data class Receita(
    @get:JvmName("getId_Receita")
    val id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val categoria: String = "", // Ex: "Café da manhã"
    val ingredientes: List<ItemReceita> = emptyList(),
    val modoDePreparo: String = ""
)
