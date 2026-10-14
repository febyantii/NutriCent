
package com.nutricent.app.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutricent.app.R
import com.nutricent.app.ui.theme.NutriCentTheme
import com.nutricent.app.ui.theme.loginBackground
import com.nutricent.app.ui.theme.loginDecoration
import com.nutricent.app.ui.theme.loginDecorationBottom
import com.nutricent.app.ui.theme.loginDecorationLight
import com.nutricent.app.ui.theme.loginDecorationPale
import com.nutricent.app.ui.theme.loginDarkGreen
import com.nutricent.app.ui.theme.loginBorder
import com.nutricent.app.ui.theme.loginGreen
import com.nutricent.app.ui.theme.loginSoftGreen
import com.nutricent.app.viewmodel.AuthMode
import com.nutricent.app.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onAuthSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val colors = MaterialTheme.colorScheme

    // Background dengan bercak pastel dekoratif
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.loginBackground)
    ) {
        // Bercak kiri atas
        Box(
            modifier = Modifier
                .offset(x = (-48).dp, y = 75.dp)
                .size(125.dp)
                .clip(CircleShape)
                .background(colors.loginDecorationLight)
        )

        // Bercak kanan atas
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 35.dp, y = 150.dp)
                .size(100.dp)
                .clip(CircleShape)
                .background(colors.loginDecorationPale)
        )

        // Bercak kiri bawah
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-22).dp, y = (-210).dp)
                .size(58.dp)
                .rotate(-35f)
                .clip(RoundedCornerShape(50))
                .background(colors.loginDecoration)
        )

        // Bercak kanan bawah
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 35.dp, y = (-40).dp)
                .size(105.dp)
                .rotate(40f)
                .clip(RoundedCornerShape(50))
                .background(colors.loginDecorationBottom)
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ---------- LOGO NUTRICENT ----------
                Image(
                    painter = painterResource(
                        id = R.drawable.logo_nutricent
                    ),
                    contentDescription = "Logo NutriCent",
                    modifier = Modifier
                        .width(220.dp)
                        .height(60.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(6.dp))

                // ---------- MASKOT ----------
                Image(
                    painter = painterResource(
                        id = R.drawable.maskot_nutricent
                    ),
                    contentDescription = "Maskot NutriCent",
                    modifier = Modifier.size(210.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ---------- SLOGAN ----------
                Text(
                    text = buildAnnotatedString {
                        pushStyle(
                            SpanStyle(
                                color = colors.onBackground
                            )
                        )
                        append("Happy Meals,\n")
                        pop()

                        pushStyle(
                            SpanStyle(
                                color = colors.tertiary
                            )
                        )
                        append("Happy Wallet")
                        pop()
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    lineHeight = MaterialTheme.typography
                        .headlineSmall.lineHeight
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ---------- EMAIL ----------
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = viewModel::onEmailChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            viewModel.onEmailFocusChange(it.isFocused)
                        },
                    label = {
                        Text("Email")
                    },
                    singleLine = true,
                    enabled = !viewModel.isLoading,
                    isError = viewModel.emailError != null,
                    supportingText = viewModel.emailError?.let { error ->
                        {
                            Text(error)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colors.loginBorder,
                        focusedBorderColor = colors.loginGreen,
                        unfocusedContainerColor = colors.surface,
                        focusedContainerColor = colors.surface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ---------- PASSWORD ----------
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = viewModel::onPasswordChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            viewModel.onPasswordFocusChange(it.isFocused)
                        },
                    label = {
                        Text("Password")
                    },
                    singleLine = true,
                    enabled = !viewModel.isLoading,
                    isError = viewModel.passwordError != null,
                    supportingText = viewModel.passwordError?.let { error ->
                        {
                            Text(error)
                        }
                    },
                    visualTransformation = if (viewModel.passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.submit(
                                AuthMode.LOGIN,
                                onAuthSuccess
                            )
                        }
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = viewModel::togglePasswordVisible
                        ) {
                            Icon(
                                imageVector = if (viewModel.passwordVisible) {
                                    Icons.Filled.VisibilityOff
                                } else {
                                    Icons.Filled.Visibility
                                },
                                contentDescription = if (
                                    viewModel.passwordVisible
                                ) {
                                    "Sembunyikan password"
                                } else {
                                    "Tampilkan password"
                                }
                            )
                        }
                    },
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colors.loginBorder,
                        focusedBorderColor = colors.loginGreen,
                        unfocusedContainerColor = colors.surface,
                        focusedContainerColor = colors.surface
                    )
                )

                // ---------- ERROR LOGIN / SIGNUP ----------
                viewModel.authError?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ---------- LOGIN ----------
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.submit(
                            AuthMode.LOGIN,
                            onAuthSuccess
                        )
                    },
                    enabled = !viewModel.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.loginGreen,
                        contentColor = colors.onPrimary
                    )
                ) {
                    if (
                        viewModel.isLoading &&
                        viewModel.activeMode == AuthMode.LOGIN
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp,
                            color = colors.onPrimary
                        )
                    } else {
                        Text(
                            "Log In",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ---------- SIGN UP ----------
                OutlinedButton(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.submit(
                            AuthMode.SIGN_UP,
                            onAuthSuccess
                        )
                    },
                    enabled = !viewModel.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colors.surface,
                        contentColor = colors.loginDarkGreen
                    ),
                    border = BorderStroke(
                        1.dp,
                        colors.loginBorder
                    )
                ) {
                    if (
                        viewModel.isLoading &&
                        viewModel.activeMode == AuthMode.SIGN_UP
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp,
                            color = colors.loginGreen
                        )
                    } else {
                        Text(
                            "Sign Up",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ---------- PEMISAH ATAU ----------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = colors.loginBorder
                    )

                    Text(
                        text = "atau",
                        modifier = Modifier.padding(horizontal = 12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.loginDarkGreen
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = colors.loginBorder
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ---------- GOOGLE ----------
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Fitur belum tersedia di tahap ini"
                            )
                        }
                    },
                    enabled = !viewModel.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colors.loginSoftGreen,
                        contentColor = colors.loginDarkGreen
                    ),
                    border = null
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Text(
                            "Continue with Google",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    NutriCentTheme {
        LoginScreen(
            onAuthSuccess = {}
        )
    }
}
