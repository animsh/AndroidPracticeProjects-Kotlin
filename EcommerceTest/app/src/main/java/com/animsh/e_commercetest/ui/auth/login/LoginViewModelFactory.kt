package com.animsh.e_commercetest.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animsh.e_commercetest.data.AuthRepository
import com.animsh.e_commercetest.data.DataStoreRepository

/**
 * Created by animsh on 4/21/2021.
 */
@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val repository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository, dataStoreRepository) as T
    }
}