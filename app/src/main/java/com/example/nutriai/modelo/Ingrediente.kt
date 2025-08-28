package com.example.nutriai.modelo

import kotlinx.serialization.Serializable

@Serializable
data class Ingrediente(
    val id: String = "",
    val nome: String = "",
    var quantidade: Float = 0f,
    val unidadeDeMedidaPadrao: String = "" // Ex: "gramas", "unidade", "ml"


){
    fun formatar(): String {
        return "$nome $quantidade $unidadeDeMedidaPadrao"
    }
}
