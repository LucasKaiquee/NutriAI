package com.example.nutriai.data

import com.google.ai.client.generativeai.GenerativeModel


object GeradorReceitas {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey =  "AIzaSyBmZ847xmXD4o4fyY2m4dW8GWN98UvAbZQ"
    )

// Dentro do object RecipeGenerator

    suspend fun generateRecipe(
        rotina: String,
        gostos: String,
        ingredientes: List<String>
    ): String { 
        try {
            val ingredientesDisponiveis = ingredientes.joinToString(", ")

            val prompt = """
            Você é um nutricionista chamado NutriAI. Sua tarefa é criar uma receita saudável e nutritiva em formato JSON.

            **Contexto do Usuário:**
            - Momento do dia: $rotina
            - Preferências: $gostos
            - Ingredientes Disponíveis: $ingredientesDisponiveis

            **Instruções:**
            1. Crie uma receita que use uma parte ou todos os ingredientes listados e somente eles, já que só tem eles disponíveis.
            2. O nome da receita deve ser simples.
            3. A categoria deve ser uma das seguintes: "Café da manhã", "Almoço", "Lanche", "Jantar".
            4. Gere uma resposta contendo APENAS um objeto JSON válido, sem nenhum texto ou formatação extra antes ou depois.
            5. O JSON deve seguir esta estrutura EXATA:
            {
              "nome": "Nome Criativo da Receita",
              "descricao": "Uma breve descrição da receita com poucas palavras.",
              "categoria": "Categoria da Receita",
              "ingredientes": [
                {
                  "nome": "Nome do Ingrediente 1",
                  "quantidade": 1.0,
                  "unidadeDeMedida": "xícara de chá"
                },
                {
                  "nome": "Nome do Ingrediente 2",
                  "quantidade": 0.5,
                  "unidadeDeMedida": "colher de sopa"
                }
              ],
              "modoDePreparo": "1. Primeiro passo...\n2. Segundo passo...\n3. Terceiro passo..."
            }
        """.trimIndent()

            val response = generativeModel.generateContent(prompt)
            return response.text ?: "{}" // Retorna um JSON vazio em caso de erro

        } catch (e: Exception) {
            println("Erro na API do Gemini: ${e.message}")
            return "{}"
        }
    }
}