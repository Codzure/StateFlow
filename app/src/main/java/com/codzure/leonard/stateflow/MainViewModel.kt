package com.codzure.leonard.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty) // Mutable UI State Flow
    val loginUiState: StateFlow<LoginUiState> = _loginUiState


    fun login(username: String, password: String){
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading

            delay(2000L)
            if (username =="Android" && password =="secret"){
                _loginUiState.value = LoginUiState.Success
            } else {
                _loginUiState.value = LoginUiState.Error("Wrong Credentials")
            }
        }

    }



    sealed class LoginUiState{
        object Success : LoginUiState()
        object Loading: LoginUiState()
        object Empty: LoginUiState()
        data class Error(val message: String): LoginUiState()
    }
}