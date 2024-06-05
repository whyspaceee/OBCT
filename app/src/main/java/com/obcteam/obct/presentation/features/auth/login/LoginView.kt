package com.obcteam.obct.presentation.features.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.obcteam.obct.R
import com.obcteam.obct.domain.mvi.CollectSideEffect
import com.obcteam.obct.domain.mvi.unpack
import com.obcteam.obct.presentation.theme.ui.OBCTTheme
import kotlinx.coroutines.launch

@Composable
fun LoginView(modifier: Modifier = Modifier, vm: LoginViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val (state, onAction, sideEffect) = vm.unpack()

    CollectSideEffect(sideEffect = sideEffect, onSideEffect = { effect ->
        when (effect) {
            is LoginSideEffect.ShowError -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    })

    val signInWithGoogleOption: GetSignInWithGoogleOption =
        GetSignInWithGoogleOption.Builder("280138413711-754tb9aiavchqibpcij9s3vhe6dpdtrh.apps.googleusercontent.com")
            .build()

    val request: GetCredentialRequest =
        GetCredentialRequest.Builder().addCredentialOption(signInWithGoogleOption).build()

    LoginView(modifier, onClickLoginWithGoogle = {
        coroutineScope.launch {
            try {
                val res = vm.credentialManager.getCredential(
                    context, request
                )
                onAction(LoginAction.LoginWithGoogleCredential(res))
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
fun LoginView(modifier: Modifier = Modifier, onClickLoginWithGoogle: () -> Unit) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = onClickLoginWithGoogle, colors = ButtonDefaults.outlinedButtonColors(
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

@Preview
@Composable
fun LoginViewPreview() {
    OBCTTheme {
        LoginView {}
    }
}