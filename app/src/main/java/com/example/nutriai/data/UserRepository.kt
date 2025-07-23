import android.util.Log
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
            .addOnFailureListener {
                Log.e("UserRepository", "Erro ao salvar usuário", it)
                onResult(false)
            }
    }

    fun addIngredient(newIngredient: Ingrediente, onResult: (Boolean) -> Unit) {
        getUser { currentUser ->
            if (currentUser != null) {
                val updatedIngredients = currentUser.ingredientes.toMutableList()

                val newId = userDoc.collection("ingredientesDisponiveis").document().id

                val ingredientWithId = newIngredient.copy(id = newId)

                updatedIngredients.add(ingredientWithId)

                val updatedUser = currentUser.copy(
                    ingredientes = updatedIngredients
                )

                saveUser(updatedUser) { success ->
                    onResult(success)
                }
            } else {
                onResult(false)
            }
        }
    }

    fun deleteIngredient(ingredientId: String, onResult: (Boolean) -> Unit) {
        getUser { currentUser ->
            if (currentUser != null) {
                val updatedIngredients = currentUser.ingredientes
                    .filterNot { it.id == ingredientId }

                val updatedUser = currentUser.copy(
                    ingredientes = updatedIngredients
                )

                saveUser(updatedUser) { success ->
                    onResult(success)
                }
            } else {
                onResult(false)
            }
        }
    }

    fun addGeneratedRecipe(newRecipe: Receita, onResult: (Boolean) -> Unit) {
        getUser { currentUser ->
            if (currentUser != null) {
                val updatedRecipes = currentUser.receitas.toMutableList()

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
