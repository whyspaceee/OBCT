package com.obcteam.obct

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.obcteam.obct.presentation.navigation.RootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(
                darkTheme = true,
                dynamicColor = false
            ) {
                val navHostController = rememberNavController()
                val mainViewModel = hiltViewModel<MainViewModel>()
                val startDestination by mainViewModel.startDestination.collectAsState()
                val authState by mainViewModel.authState.collectAsState()

                Surface (
                    modifier = Modifier.fillMaxSize(),
                ) {
                    startDestination?.let {
                        RootNavigationGraph(
                            navHostController = navHostController,
                            startDestination = it
                        )
                    }
                }




            }
        }
    }
}
