package com.obcteam.obct

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.obcteam.obct.presentation.navigation.Graph
import com.obcteam.obct.presentation.navigation.RootNavigationGraph
import com.obcteam.obct.presentation.theme.ui.OBCTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OBCTTheme {
                val navHostController = rememberNavController()
                val mainViewModel = hiltViewModel<MainViewModel>()
                val startDestination by mainViewModel.startDestination.collectAsState()
                val authState by mainViewModel.authState.collectAsState()


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
