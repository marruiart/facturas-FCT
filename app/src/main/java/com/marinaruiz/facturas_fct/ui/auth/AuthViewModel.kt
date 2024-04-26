package com.marinaruiz.facturas_fct.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.data.network.firebase.model.LoginResult
import com.marinaruiz.facturas_fct.domain.LoginUseCase
import com.marinaruiz.facturas_fct.domain.SignUpUseCase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val loginUseCase = LoginUseCase()
    private val signUpUseCase = SignUpUseCase()

    private val _allowAccess = MutableLiveData(false)
    val allowAccess: LiveData<Boolean> = _allowAccess

    private val _showErrorDialog = MutableLiveData<ErrorResponse?>()
    val showErrorDialog: LiveData<ErrorResponse?> = _showErrorDialog

    private val observer = Observer<String?> { uid ->
        _allowAccess.value = uid != null
    }

    companion object {
        private const val TAG = "VIEWNEXT AuthViewModel"
    }

    init {

        loginUseCase.uid.observeForever(observer)
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase(email, password)
            when (result) {
                is LoginResult.Error -> {
                    _showErrorDialog.value =
                        ErrorResponse("LOGIN_ERROR", LoginResult.Error.toString())
                }

                is LoginResult.Success -> {
                    Log.d(TAG, "Login Success")
                }
            }
        }
    }


    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val result = signUpUseCase(email, password)
            if (result.error != null) {
                _showErrorDialog.value = result.error
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loginUseCase.uid.removeObserver(observer)
    }


}

