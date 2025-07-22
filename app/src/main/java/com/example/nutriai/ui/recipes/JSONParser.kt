package com.example.nutriai.ui.recipes

import com.example.nutriai.modelo.Receita
import kotlinx.serialization.json.Json


sealed class ParseResult {
    data class Success(val recipe: Receita) : ParseResult()
    data class Error(val exception: Exception) : ParseResult()
}

object JSONParser {
    private val json = Json { ignoreUnknownKeys = true }

    fun parse(jsonString: String): ParseResult {
        return try {
            ParseResult.Success(json.decodeFromString<Receita>(jsonString))
        } catch (e: Exception) {
            println("EXCEÇÃO DO PARSER: $e")
            ParseResult.Error(e)
        }
    }
}