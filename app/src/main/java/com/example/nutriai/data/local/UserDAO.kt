package com.example.nutriai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nutriai.modelo.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Query("SELECT * FROM usuario WHERE id = :userId")
    fun getObservableUser(userId: String): Flow<Usuario?>

    @Query("SELECT * FROM usuario WHERE id = :userId")
    suspend fun getUser(userId: String): Usuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: Usuario)
}