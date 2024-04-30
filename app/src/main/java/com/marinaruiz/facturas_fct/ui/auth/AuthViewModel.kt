package com.marinaruiz.facturas_fct.ui.auth

import android.content.Context
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marinaruiz.facturas_fct.core.ErrorResponse
import com.marinaruiz.facturas_fct.core.SecureSharedPrefs
import com.marinaruiz.facturas_fct.data.network.firebase.model.LoginResult
import com.marinaruiz.facturas_fct.domain.LoginUseCase
import com.marinaruiz.facturas_fct.domain.SignUpUseCase
import com.marinaruiz.facturas_fct.domain.TogglePasswordVisibilityUseCase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val loginUseCase = LoginUseCase()
    private val signUpUseCase = SignUpUseCase()
    private val togglePasswordVisibilityUseCase = TogglePasswordVisibilityUseCase()

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

    fun showPassword(cbToggle: CheckBox, isChecked: Boolean, vararg editTexts: EditText) {
        togglePasswordVisibilityUseCase(cbToggle, isChecked, *editTexts)
    }

    fun saveInSecSharedPrefs(context: Context, key: String, data: String?) {
        val sharedPreferences = SecureSharedPrefs.getSecSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, data)
        editor.apply()
    }

    fun retrieveFromSecSharedPreferences(context: Context, key: String): String? {
        val sharedPreferences = SecureSharedPrefs.getSecSharedPreferences(context)
        return sharedPreferences.getString(key, null)
    }

    override fun onCleared() {
        super.onCleared()
        loginUseCase.uid.removeObserver(observer)
    }


}

