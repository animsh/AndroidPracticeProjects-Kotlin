package com.animsh.e_commercetest.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.animsh.e_commercetest.data.AuthRepository
import com.animsh.e_commercetest.data.DataStoreRepository
import com.animsh.e_commercetest.entities.FAuth
import com.animsh.e_commercetest.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by animsh on 4/21/2021.
 */
class SignUpViewModel(
    private val repository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var authenticatedUserLiveData: MutableLiveData<FAuth> = MutableLiveData()
    var dataStoreLiveData: MutableLiveData<FAuth> = MutableLiveData()

    fun signUpUser(user: User) = CoroutineScope(Dispatchers.Main).launch {
        authenticatedUserLiveData = repository.signUp(user)
    }

    fun createUser(user: User) = CoroutineScope(Dispatchers.Main).launch {
        dataStoreLiveData = dataStoreRepository.createUser(user)
    }

}