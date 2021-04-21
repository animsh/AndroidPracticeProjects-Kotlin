package com.animsh.e_commercetest.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animsh.e_commercetest.data.AuthRepository
import com.animsh.e_commercetest.data.DataStoreRepository

/**
 * Created by animsh on 4/21/2021.
 */
@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory(
    private val repository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository, dataStoreRepository) as T
    }
}