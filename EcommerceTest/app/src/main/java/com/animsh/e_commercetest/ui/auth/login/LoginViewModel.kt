package com.animsh.e_commercetest.ui.auth.login

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
class LoginViewModel(
    private val repository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var authenticatedUserLiveData: MutableLiveData<FAuth> = MutableLiveData()
    var dataStoreLiveData: MutableLiveData<FAuth> = MutableLiveData()

    fun loginUser(user: User) = CoroutineScope(Dispatchers.Main).launch {
        authenticatedUserLiveData = repository.login(user)
    }

    fun createUser(user: User) = CoroutineScope(Dispatchers.Main).launch {
        dataStoreLiveData = dataStoreRepository.createUser(user)
    }

}