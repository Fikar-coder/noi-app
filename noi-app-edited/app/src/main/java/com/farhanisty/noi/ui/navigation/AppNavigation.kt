package com.farhanisty.noi.ui.navigation

sealed class Screen(val route: String) {
    object MatkulList : Screen("matkul_list")
    object AddMatkul : Screen("add_matkul")
    object EditMatkul : Screen("edit_matkul/{matkulId}") {
        fun createRoute(matkulId: Int) = "edit_matkul/$matkulId"
    }
}
