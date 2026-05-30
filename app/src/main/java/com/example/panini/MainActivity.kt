package com.example.panini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.panini.ui.screens.AuthViewModel
import com.example.panini.ui.screens.CreateTicketScreen
import com.example.panini.ui.screens.LoginScreen
import com.example.panini.ui.screens.TicketDetailScreen
import com.example.panini.ui.screens.TicketListScreen
import com.example.panini.ui.screens.TicketViewModel
import com.example.panini.ui.theme.PaniniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaniniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()
                    val ticketViewModel: TicketViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                viewModel = authViewModel,
                                onLoginSuccess = {
                                    navController.navigate("tickets") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("tickets") {
                            TicketListScreen(
                                viewModel = ticketViewModel,
                                onNavigateToDetail = { ticketId ->
                                    navController.navigate("detail/$ticketId")
                                },
                                onNavigateToCreate = {
                                    navController.navigate("create")
                                }
                            )
                        }
                        composable("detail/{ticketId}") { backStackEntry ->
                            val ticketId = backStackEntry.arguments?.getString("ticketId") ?: ""
                            TicketDetailScreen(
                                ticketId = ticketId,
                                viewModel = ticketViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("create") {
                            CreateTicketScreen(
                                viewModel = ticketViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PaniniTheme {
        Greeting("Android")
    }
}