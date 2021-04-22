package com.animsh.e_commercevendor.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animsh.e_commercevendor.data.AuthRepository

/**
 * Created by animsh on 4/21/2021.
 */
@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val repository: AuthRepository,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}