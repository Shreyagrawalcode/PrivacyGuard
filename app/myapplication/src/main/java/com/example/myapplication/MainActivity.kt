package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.PrivacyGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrivacyGuardTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "scanner",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("scanner") {
                            AppScannerScreen(
                                onAppClick = { appName, permissions ->
                                    navController.currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("permissions", ArrayList(permissions))
                                    navController.navigate("detail/$appName")
                                }
                            )
                        }
                        composable("detail/{appName}") { backStackEntry ->
                            val appName = backStackEntry.arguments?.getString("appName") ?: ""
                            val permissions = navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.get<ArrayList<String>>("permissions")
                                ?: arrayListOf()
                            AppDetailScreen(
                                appName = appName,
                                permissions = permissions
                            )
                        }
                    }
                }
            }
        }
    }
}