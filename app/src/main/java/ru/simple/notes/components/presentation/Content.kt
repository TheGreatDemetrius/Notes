package ru.simple.notes.components.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.simple.notes.components.presentation.item.ItemScreen
import ru.simple.notes.components.presentation.list.ListScreen
import ru.simple.notes.components.presentation.util.Navigation

@ExperimentalAnimationApi
@Composable
fun Content() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Navigation.ListScreen.route
    ) {
        composable(route = Navigation.ListScreen.route) {
            ListScreen(navController = navController)
        }
        composable(
            route = Navigation.ItemScreen.route +
                    "?noteId={noteId}&noteColor={noteColor}",//необязательные аргументы
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val color = it.arguments?.getInt("noteColor") ?: -1
            ItemScreen(color = color, navController = navController)
        }
    }
}