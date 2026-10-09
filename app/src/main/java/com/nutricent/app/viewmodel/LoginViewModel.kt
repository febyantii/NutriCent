package com.nutricent.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutricent.app.data.DummyData
import com.nutricent.app.model.DummyAccount
import com.nutricent.app.util.Validators
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class AuthMode { LOGIN, SIGN_UP }

class LoginViewModel : ViewModel() {

    // ---------- Input state ----------
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordVisible by mutableStateOf(false)
        private set

    // ---------- Touched state (error muncul setelah field disentuh / submit) ----------
    var emailTouched by mutableStateOf(false)
        private set
    var passwordTouched by mutableStateOf(false)
        private set
    private var emailWasFocused = false
    private var passwordWasFocused = false

    // ---------- Loading & hasil auth ----------
    var isLoading by mutableStateOf(false)
        private set
    var activeMode by mutableStateOf(AuthMode.LOGIN)
        private set
    var authError by mutableStateOf<String?>(null)
        private set

    // ---------- Validasi (dihitung dari state, otomatis ikut recompose) ----------
    val emailError: String?
        get() = if (emailTouched) Validators.validateEmail(email) else null
    val passwordError: String?
        get() = if (passwordTouched) Validators.validatePassword(password) else null

    val isFormValid: Boolean
        get() = Validators.validateEmail(email) == null &&
                Validators.validatePassword(password) == null

    // ---------- Events ----------
    fun onEmailChange(value: String) {
        email = value
        emailTouched = true
        authError = null
    }

    fun onPasswordChange(value: String) {
        password = value
        passwordTouched = true
        authError = null
    }

    fun onEmailFocusChange(focused: Boolean) {
        if (focused) emailWasFocused = true
        else if (emailWasFocused) emailTouched = true
    }

    fun onPasswordFocusChange(focused: Boolean) {
        if (focused) passwordWasFocused = true
        else if (passwordWasFocused) passwordTouched = true
    }

    fun togglePasswordVisible() { passwordVisible = !passwordVisible }

    /** Dipanggil tombol Log In / Sign Up. Diblok kalau form belum valid. */
    fun submit(mode: AuthMode, onSuccess: () -> Unit) {
        if (isLoading) return
        emailTouched = true
        passwordTouched = true
        authError = null
        if (!isFormValid) return   // submit diblok, error tampil di field

        viewModelScope.launch {
            activeMode = mode
            isLoading = true
            delay(1000) // simulasi proses jaringan

            val cleanEmail = email.trim()
            val accounts = DummyData.accounts
            val exists = accounts.any { it.email.equals(cleanEmail, ignoreCase = true) }

            val success = when (mode) {
                AuthMode.LOGIN -> {
                    val ok = accounts.any {
                        it.email.equals(cleanEmail, ignoreCase = true) && it.password == password
                    }
                    if (!ok) authError = "Email atau password salah"
                    ok
                }
                AuthMode.SIGN_UP -> {
                    if (exists) {
                        authError = "Email sudah terdaftar"
                        false
                    } else {
                        accounts.add(DummyAccount(cleanEmail, password))
                        true
                    }
                }
            }
            isLoading = false
            if (success) onSuccess()
        }
    }
}
