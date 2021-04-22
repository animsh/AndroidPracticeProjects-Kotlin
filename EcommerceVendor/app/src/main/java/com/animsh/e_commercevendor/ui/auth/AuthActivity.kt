package com.animsh.e_commercevendor.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.animsh.e_commercetest.entities.User
import com.animsh.e_commercetest.utils.LoadingDialog
import com.animsh.e_commercevendor.R
import com.animsh.e_commercevendor.databinding.ActivityAuthBinding
import com.animsh.e_commercevendor.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class AuthActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityAuthBinding

    override val kodein by closestKodein()
    private val factory: LoginViewModelFactory by instance()
    private lateinit var viewModel: LoginViewModel

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.apply {
            val sharedPref =
                getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
                    ?: return
            val isUserLogin = sharedPref.getBoolean("userLogin", false)
            Log.d("TAGTAGTAG", "onCreate: $isUserLogin")
            if (isUserLogin) {
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                finish()
            } else {
                loadingDialog = LoadingDialog(this@AuthActivity)
                createAccount.paint.isUnderlineText = true
                viewModel =
                    ViewModelProvider(this@AuthActivity, factory).get(LoginViewModel::class.java)
                loginButton.setOnClickListener {
                    loadingDialog.showLoadingDialog()
                    val uEmail = editTextEmailID.text
                    val uPassword = editTextPassword.text

                    if (uEmail.isNullOrEmpty() || uPassword.isNullOrEmpty()) {
                        Toast.makeText(
                            this@AuthActivity,
                            "Please fill all the details!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        loadingDialog.dismissDialog()
                        return@setOnClickListener
                    } else {
                        if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
                            Toast.makeText(
                                this@AuthActivity,
                                "Please use valid email address!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            loadingDialog.dismissDialog()
                            return@setOnClickListener
                        }
                    }

                    viewModel.loginUser(
                        User(
                            email = uEmail.toString(),
                            password = uPassword.toString()
                        )
                    ).invokeOnCompletion {
                        viewModel.authenticatedUserLiveData.observe(
                            this@AuthActivity,
                            { result ->
                                if (result.isSuccess) {
                                    saveLoginState()
                                    loadingDialog.dismissDialog()
                                    startActivity(
                                        Intent(
                                            this@AuthActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@AuthActivity,
                                        result.msg,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    loadingDialog.dismissDialog()
                                }
                            })
                    }
                }
            }


        }
    }

    private fun saveLoginState() {
        val sharedPref =
            getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        with(sharedPref?.edit()) {
            this?.putBoolean("userLogin", true)
            this?.apply()
        }
    }

}