// app/src/main/java/com/example/nutriai/di/AppModule.kt

package com.example.nutriai.di

import com.example.nutriai.data.UserRepository
import com.example.nutriai.data.local.NutriAIDatabase
import com.example.nutriai.ui.recipes.GenerateRecipeViewModel
import com.example.nutriai.ui.screens.home.IngredientViewModel
import com.example.nutriai.ui.screens.profile.ProfileViewModel
import com.example.nutriai.viewmodel.ReceitaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { NutriAIDatabase.getDatabase(androidContext()).userDao() }
    single { UserRepository(get()) }

    viewModel { ReceitaViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { IngredientViewModel(get()) }
    viewModel { GenerateRecipeViewModel(get()) }
}