package com.example.nutriai.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nutriai.ui.recipes.GenerateRecipeScreen
import com.example.nutriai.ui.screens.profile.ProfileEditScreen
import com.example.nutriai.ui.screens.profile.ProfileScreenContent
import com.example.nutriai.ui.screens.home.HomeScreen
import com.example.nutriai.ui.screens.home.AddIngredientScreen
import com.example.nutriai.ui.screens.home.IngredientListScreen
import com.example.nutriai.ui.screens.lists.RecipeListScreen

@Composable
fun Navigation(navController: NavHostController,
               paddingValues: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("listas") {
            RecipeListScreen()
        }
        composable("perfil") {
            ProfileScreenContent(navController)
        }
        composable("editProfile") {
            ProfileEditScreen(navController)
        }
        composable("add_ingredient") {
            AddIngredientScreen(navController = navController)
        }
        composable("ingredientes") {
            IngredientListScreen(navController = navController)
        }
        composable("generate_recipe") {
            GenerateRecipeScreen(navController = navController)
        }
    }
}