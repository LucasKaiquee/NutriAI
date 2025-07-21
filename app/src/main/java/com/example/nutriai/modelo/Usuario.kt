package com.example.nutriai.modelo

import com.google.firebase.firestore.Exclude

data class Usuario(
    val name: String = "",
    val age: Int = 0,
    val routine: String = "",
    val preferences: String = "",
    val ingredientes: List<Ingrediente> = ArrayList(),
    val receitas: List<Receita> = ArrayList()
)
