package com.obcteam.obct.presentation.features.auth.login

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.obcteam.obct.R
import kotlinx.coroutines.launch

@Composable
fun LoginView(modifier: Modifier = Modifier, vm: LoginViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isLoading = vm.isLoading.collectAsState()
//    val (state, onAction, sideEffect) = vm.unpack()

//    CollectSideEffect(sideEffect = sideEffect, onSideEffect = { effect ->
//        when (effect) {
//            is LoginSideEffect.ShowError -> {
//                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//    })

    val signInWithGoogleOption: GetSignInWithGoogleOption =
        GetSignInWithGoogleOption.Builder("280138413711-754tb9aiavchqibpcij9s3vhe6dpdtrh.apps.googleusercontent.com")
            .build()

    val request: GetCredentialRequest =
        GetCredentialRequest.Builder().addCredentialOption(signInWithGoogleOption).build()

    LoginView(modifier = modifier,
        isLoading = isLoading.value,
        onClickLoginWithGoogle = {
            coroutineScope.launch {
                try {
                    val res = vm.credentialManager.getCredential(
                        context, request
                    )
                    vm.loginWithGoogleCredential(res)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.failed_to_sign_in_with_a_google_account),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
}

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    onClickLoginWithGoogle: () -> Unit,
    isLoading: Boolean
) {
    Scaffold(modifier = modifier) { paddingValues ->
        AnimatedContent(
            targetState = isLoading,
            label = "login",
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = fadeIn(tween(1000)),
                    initialContentExit = fadeOut(tween(1000))
                )
            }) { targetState ->
            if (targetState) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(shimmerBrush())
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.obct),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(96.dp).align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.width(250.dp),
                            text = "Weight loss can be easy",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 40.sp,
                                lineHeight = 48.sp,
                                brush = Brush.verticalGradient(
                                    0.5f to MaterialTheme.colorScheme.primary,
                                    1f to MaterialTheme.colorScheme.tertiary
                                )
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Start measuring your weight and let our AI model do the rest")
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClickLoginWithGoogle,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_logo_google_g_icon),
                            contentDescription = stringResource(R.string.google_logo_icon)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(stringResource(R.string.sign_in_with_google))
                    }
                }
            }

        }

    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f),
            MaterialTheme.colorScheme.background,
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = 2000f,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ), label = "shimmer"
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}


