package com.animsh.e_commercetest.data

import androidx.lifecycle.MutableLiveData
import com.animsh.e_commercetest.entities.FAuth
import com.animsh.e_commercetest.entities.User
import com.google.firebase.auth.FirebaseAuth


/**
 * Created by animsh on 4/11/2021.
 */
class AuthRepository {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun login(user: User): MutableLiveData<FAuth> {
        val fAuth: MutableLiveData<FAuth> = MutableLiveData()
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                val currentUser = currentUser()
                val isLogin = currentUser != null
                val msg = it.exception?.message.toString()
                fAuth.value = FAuth(isLogin, msg)
            } else {
                val msg = it.exception?.message.toString()
                fAuth.value = FAuth(msg = msg)
            }
        }
        return fAuth
    }

    fun signUp(user: User): MutableLiveData<FAuth> {
        val fAuth: MutableLiveData<FAuth> = MutableLiveData()
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val isSuccess = it.result?.user?.uid != null
                    val msg = it.result?.user?.uid.toString()
                    fAuth.value = FAuth(isSuccess, msg)
                } else {
                    val msg = it.exception?.message.toString()
                    fAuth.value = FAuth(msg = msg)
                }
            }

        return fAuth
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}