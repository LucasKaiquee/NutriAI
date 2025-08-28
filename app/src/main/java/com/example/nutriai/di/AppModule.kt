package com.example.nutriai.di

import com.example.nutriai.data.UserRepository
import com.example.nutriai.viewmodel.ReceitaViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.viewModel
import com.example.nutriai.viewmodel.ProfileViewModel
import com.example.nutriai.viewmodel.IngredientViewModel
import org.koin.dsl.module
import com.example.nutriai.ui.recipes.GenerateRecipeViewModel

val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single { UserRepository(get()) }

    // registra o ViewModel corretamente
    viewModel { ReceitaViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { IngredientViewModel(get()) }
    viewModel { GenerateRecipeViewModel(get()) }

}
