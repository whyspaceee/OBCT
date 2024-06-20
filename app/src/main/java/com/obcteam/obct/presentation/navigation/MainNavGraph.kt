package com.obcteam.obct.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arnyminerz.markdowntext.MarkdownText
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.obcteam.obct.AuthState
import com.obcteam.obct.MainViewModel
import com.obcteam.obct.R
import com.obcteam.obct.domain.utils.OBCTFormatters
import com.obcteam.obct.presentation.features.chat.ChatInputView
import com.obcteam.obct.presentation.features.chat.ChatInputViewModel
import com.obcteam.obct.presentation.features.history.HistoryView
import com.obcteam.obct.presentation.features.history.HistoryViewModel

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootNavController: NavHostController
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            route = Graph.MAIN,
            startDestination = MainScreen.Chat.route
        ) {
            composable(route = MainScreen.Home.route) {
                val mainViewModel = hiltViewModel<MainViewModel>(
                    viewModelStoreOwner = LocalContext.current as androidx.activity.ComponentActivity
                )
                val authState = mainViewModel.authState.collectAsState()
                when (authState.value) {

                    is AuthState.Authenticated -> {
                        val currentUser = (authState.value as AuthState.Authenticated).user
                        val lastPred = (authState.value as AuthState.Authenticated).lastPrediction
                        Column {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState()),
                            ) {

                                Row {
                                    Column {
                                        Text(
                                            text = "Hello,",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = "${currentUser.name}",
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )


                                    }
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxWidth()
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(currentUser.picture)
                                                .crossfade(true).build(),
                                            contentDescription = stringResource(R.string.profile_picture),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(40.dp)
                                                .align(Alignment.CenterEnd)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                ElevatedCard(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(modifier = Modifier.padding(16.dp)) {
                                        Column {
                                            Text("Your Progress")
                                            Text(
                                                text = "${lastPred.weight} kg",
                                                style = MaterialTheme.typography.headlineLarge.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = MaterialTheme.colorScheme.tertiary
                                                )
                                            )
                                            Text(
                                                text =
                                                OBCTFormatters.formatPredictionToString(
                                                    lastPred.prediction?.prediction ?: 0
                                                ),
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            )


                                        }
                                    }

                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        MarkdownText(markdown = lastPred.prediction?.recommendation?.map { it.recommendation }
                                            ?.joinToString(", ") ?: "",
                                            style = MaterialTheme.typography.bodyLarge)
                                    }

                                }


                            }

                        }
                    }

                    else -> {

                    }
                }


            }
            composable(route = MainScreen.Settings.route) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Button(onClick = {
                        Firebase.auth.signOut()
                    }) {
                        Text(text = stringResource(R.string.sign_out))
                    }
                }
            }
            composable(route = MainScreen.History.route) {
                val vm = hiltViewModel<HistoryViewModel>()
                HistoryView(vm = vm)


            }
            composable(route = MainScreen.Chat.route) {
                val vm = hiltViewModel<ChatInputViewModel>()
                val mainViewModel = hiltViewModel<MainViewModel>(
                    viewModelStoreOwner = LocalContext.current as androidx.activity.ComponentActivity
                )
                ChatInputView(
                    viewModel = vm,
                    mainViewModel = mainViewModel,
                    rootNavController = rootNavController
                )
            }


        }
    }

}

sealed class MainScreen(val route: String) {
    object Home : MainScreen("home")
    object Settings : MainScreen("settings")
    object History : MainScreen("history")
    object Chat : MainScreen("chat")

    fun withArgs(vararg args: String): String {
        return "$route/${args.joinToString("/")}"
    }
}
