package com.animsh.e_commercetest.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.animsh.e_commercetest.ui.home.MainActivity
import com.animsh.e_commercetest.R
import com.animsh.e_commercetest.databinding.FragmentLoginBinding
import com.animsh.e_commercetest.entities.User
import com.animsh.e_commercetest.utils.LoadingDialog
import com.google.android.material.transition.MaterialFadeThrough
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


/**
 * Created by animsh on 4/21/2021.
 */
class LoginFragment : Fragment(R.layout.fragment_login), KodeinAware {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override val kodein by closestKodein()
    private val factory: LoginViewModelFactory by instance()
    private lateinit var viewModel: LoginViewModel

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
        viewModel = ViewModelProvider(requireActivity(), factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loadingDialog = LoadingDialog(requireActivity())

            loginButton.setOnClickListener {
                loadingDialog.showLoadingDialog()
                val uEmail = editTextEmailID.text
                val uPassword = editTextPassword.text

                if (uEmail.isNullOrEmpty() || uPassword.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Please fill all the details!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadingDialog.dismissDialog()
                    return@setOnClickListener
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
                        Toast.makeText(
                            requireContext(),
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
                        viewLifecycleOwner,
                        { result ->
                            if (result.isSuccess) {
                                saveLoginState()
                                loadingDialog.dismissDialog()
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                                activity?.finish()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    result.msg,
                                    Toast.LENGTH_SHORT
                                ).show()
                                loadingDialog.dismissDialog()
                            }
                        })
                }
            }

            registerButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
        }
    }

    private fun saveLoginState() {
        val sharedPref =
            activity?.getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        with(sharedPref?.edit()) {
            this?.putBoolean("userLogin", true)
            this?.apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}