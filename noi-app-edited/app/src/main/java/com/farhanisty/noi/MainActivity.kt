package com.farhanisty.noi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.farhanisty.noi.ui.matkul.add.AddMatkulScreen
import com.farhanisty.noi.ui.matkul.edit.EditMatkulScreen
import com.farhanisty.noi.ui.matkul.list.MatkulListScreen
import com.farhanisty.noi.ui.navigation.Screen
import com.farhanisty.noi.ui.theme.NoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoiTheme { NoiApp() }
        }
    }
}

@Composable
fun NoiApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MatkulList.route
    ) {
        composable(Screen.MatkulList.route) {
            MatkulListScreen(
                onAddMatkul = { navController.navigate(Screen.AddMatkul.route) },
                onEditMatkul = { id -> navController.navigate(Screen.EditMatkul.createRoute(id)) }
            )
        }
        composable(Screen.AddMatkul.route) {
            AddMatkulScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.EditMatkul.route,
            arguments = listOf(navArgument("matkulId") { type = NavType.IntType })
        ) {
            EditMatkulScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
