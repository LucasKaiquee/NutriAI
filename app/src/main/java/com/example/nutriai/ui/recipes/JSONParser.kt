package com.example.nutriai.ui.recipes

// Em um novo arquivo, ex: data/RecipeParser.kt
import com.example.nutriai.modelo.Receita
import kotlinx.serialization.json.Json


sealed class ParseResult {
    data class Success(val recipe: Receita) : ParseResult()
    data class Error(val exception: Exception) : ParseResult()
}

object JSONParser {
    private val json = Json { ignoreUnknownKeys = true }

    // A função agora retorna nosso ParseResult
    fun parse(jsonString: String): ParseResult {
        return try {
            // Se o parse funcionar, retorna o sucesso com o objeto
            ParseResult.Success(json.decodeFromString<Receita>(jsonString))
        } catch (e: Exception) {
            // Se falhar, retorna o erro com a exceção exata
            println("EXCEÇÃO DO PARSER: $e") // Imprime o erro completo no Logcat
            ParseResult.Error(e)
        }
    }
}