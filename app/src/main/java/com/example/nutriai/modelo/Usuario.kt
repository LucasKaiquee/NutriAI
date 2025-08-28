package com.example.nutriai.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey val id: String = "FfyTYvx2zqWZSCi4Sg04DDK62B93",
    val name: String = "",
    val age: Int = 0,
    val routine: String = "",
    val preferences: String = "",
    val ingredientes: List<Ingrediente> = ArrayList(),
    val receitas: List<Receita> = ArrayList()
)
