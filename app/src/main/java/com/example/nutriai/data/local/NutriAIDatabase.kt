package com.example.nutriai.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nutriai.modelo.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NutriAIDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: NutriAIDatabase? = null

        fun getDatabase(context: Context): NutriAIDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NutriAIDatabase::class.java,
                    "nutriai_database"
                )
                    .addCallback(DatabaseCallback(context)) // Adicionamos o Callback aqui
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.userDao())
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDAO) {
            // Cria e insere o usuário padrão.
            // Você pode preencher com os dados que desejar.
            val defaultUser = Usuario(
                id = "FfyTYvx2zqWZSCi4Sg04DDK62B93", // Mesmo ID fixo de antes
                name = "Usuário Padrão",
                age = 30,
                routine = "Ativo",
                preferences = "Gosto de comidas leves e saudáveis.",
                ingredientes = emptyList(),
                receitas = emptyList()
            )
            userDao.saveUser(defaultUser)
        }
    }
}