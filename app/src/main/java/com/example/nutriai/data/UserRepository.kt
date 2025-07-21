import com.example.nutriai.modelo.Ingrediente
import com.example.nutriai.modelo.Receita
import com.example.nutriai.modelo.Usuario
import com.google.firebase.firestore.FirebaseFirestore

object UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val userDoc = db.collection("usuarios").document("FfyTYvx2zqWZSCi4Sg04DDK62B93")

    fun getUser(onResult: (Usuario?) -> Unit) {
        userDoc.get()
            .addOnSuccessListener { document ->
                onResult(document.toObject(Usuario::class.java))
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun saveUser(user: Usuario, onResult: (Boolean) -> Unit) {
        userDoc.set(user)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun addIngredient(newIngredient: Ingrediente, onResult: (Boolean) -> Unit) {
        // Passo 1: LER o usuário atual.
        getUser { currentUser ->
            if (currentUser != null) {
                // Passo 2: MODIFICAR a lista de ingredientes na memória.

                // Pega a lista atual e a torna mutável para podermos adicionar um item.
                val updatedIngredients = currentUser.ingredientes.toMutableList()

                val newId = userDoc.collection("ingredientesDisponiveis").document().id

                // Cria a cópia do ingrediente com o ID que AGORA SERÁ SALVO.
                val ingredientWithId = newIngredient.copy(id = newId)

                updatedIngredients.add(ingredientWithId)

                // Cria uma cópia do usuário com a lista de ingredientes atualizada.
                val updatedUser = currentUser.copy(
                    ingredientes = updatedIngredients
                )

                // Passo 3: ESCREVER o objeto de usuário completo de volta.
                saveUser(updatedUser) { success ->
                    // Repassa o resultado da operação de salvar para o callback final.
                    onResult(success)
                }
            } else {
                // Se não foi possível ler o usuário, a operação falha.
                onResult(false)
            }
        }
    }

    fun deleteIngredient(ingredientId: String, onResult: (Boolean) -> Unit) {
        // Passo 1: LER o usuário atual.
        getUser { currentUser ->
            if (currentUser != null) {
                // Passo 2: MODIFICAR a lista, removendo o ingrediente com o ID correspondente.
                val updatedIngredients = currentUser.ingredientes
                    .filterNot { it.id == ingredientId } // filterNot é uma forma segura de remover

                // Cria uma cópia do usuário com a lista atualizada.
                val updatedUser = currentUser.copy(
                    ingredientes = updatedIngredients
                )

                // Passo 3: ESCREVER o objeto de usuário completo de volta.
                saveUser(updatedUser) { success ->
                    onResult(success)
                }
            } else {
                // Se o usuário não for encontrado, a operação falha.
                onResult(false)
            }
        }
    }

    fun addGeneratedRecipe(newRecipe: Receita, onResult: (Boolean) -> Unit) {
        getUser { currentUser ->
            if (currentUser != null) {
                val updatedRecipes = currentUser.receitas.toMutableList()

                // Gera um ID para a nova receita
                val newId = userDoc.collection("receitasSalvas").document().id
                val recipeWithId = newRecipe.copy(id = newId)

                updatedRecipes.add(recipeWithId)

                val updatedUser = currentUser.copy(receitas = updatedRecipes)
                saveUser(updatedUser, onResult)
            } else {
                onResult(false)
            }
        }
    }
}
