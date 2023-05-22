package com.example.textparsingapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.textparsingapp.ui.screen.text_parsing.view.TextParsingViewModel
import com.example.textparsingapp.ui.screen.text_parsing.view.TextParsingRoute
import com.example.textparsingapp.ui.theme.TextParsingAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.textparsingapp.ui.screen.bit_manipulation.view.BitManipulationRoute
import com.example.textparsingapp.ui.screen.bit_manipulation.view.BitManipulationViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val textParsingViewModel: TextParsingViewModel by viewModels()
    private val bitManipulationViewModel: BitManipulationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TextParsingAppTheme {
                MainScreenView()
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainScreenView() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) {
            NavigationGraph(navController = navController)
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController, startDestination = BottomNavItem.TextParsing.screen_route) {
            composable(BottomNavItem.TextParsing.screen_route) {
                TextParsingRoute(viewModel = textParsingViewModel)
            }
            composable(BottomNavItem.BitManipulation.screen_route) {
                BitManipulationRoute(viewModel = bitManipulationViewModel)
            }
        }
    }

    @Composable
    fun BottomNavigation(navController: NavController) {
        val items = listOf(
            BottomNavItem.TextParsing,
            BottomNavItem.BitManipulation
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.teal_200),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 10.sp
                        )
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}