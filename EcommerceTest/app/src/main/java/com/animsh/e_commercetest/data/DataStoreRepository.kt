package com.animsh.e_commercetest.data

import androidx.lifecycle.MutableLiveData
import com.animsh.e_commercetest.entities.FAuth
import com.animsh.e_commercetest.entities.User
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by animsh on 4/15/2021.
 */
class DataStoreRepository {
    private val TAG: String = "TAGTAGTAG"
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun createUser(user: User): MutableLiveData<FAuth> {
        val fAuth: MutableLiveData<FAuth> = MutableLiveData()

        val userMap = hashMapOf(
            "uId" to user.uid,
            "uName" to user.name,
            "uEmail" to user.email,
            "uMobileNumber" to user.mobile
        )

        firebaseFirestore.collection("users").document(user.uid)
            .set(userMap)
            .addOnSuccessListener {
                val isSuccess = true
                fAuth.value = FAuth(isSuccess)
            }.addOnFailureListener {
                val msg = it.message.toString()
                fAuth.value = FAuth(msg = msg)
            }
        return fAuth
    }
}