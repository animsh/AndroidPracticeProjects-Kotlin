package com.animsh.e_commercevendor.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.animsh.e_commercetest.entities.User
import com.animsh.e_commercevendor.data.AuthRepository
import com.animsh.e_commercevendor.entities.FAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Created by animsh on 4/21/2021.
 */
class LoginViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    var authenticatedUserLiveData: MutableLiveData<FAuth> = MutableLiveData()

    fun loginUser(user: User) = CoroutineScope(Dispatchers.Main).launch {
        authenticatedUserLiveData = repository.login(user)
    }

}