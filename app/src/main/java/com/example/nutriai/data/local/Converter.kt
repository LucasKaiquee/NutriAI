package com.example.nutriai.data.local

import androidx.room.TypeConverter
import com.example.nutriai.modelo.Ingrediente
import com.example.nutriai.modelo.ItemReceita
import com.example.nutriai.modelo.Receita
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromIngredientList(ingredients: List<Ingrediente>): String {
        return json.encodeToString(ingredients)
    }

    @TypeConverter
    fun toIngredientList(jsonString: String): List<Ingrediente> {
        return json.decodeFromString(jsonString)
    }

    @TypeConverter
    fun fromRecipeList(recipes: List<Receita>): String {
        return json.encodeToString(recipes)
    }

    @TypeConverter
    fun toRecipeList(jsonString: String): List<Receita> {
        return json.decodeFromString(jsonString)
    }

    @TypeConverter
    fun fromItemReceitaList(items: List<ItemReceita>): String {
        return json.encodeToString(items)
    }

    @TypeConverter
    fun toItemReceitaList(jsonString: String): List<ItemReceita> {
        return json.decodeFromString(jsonString)
    }
}