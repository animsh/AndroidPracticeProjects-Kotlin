package com.animsh.e_commercetest

import android.app.Application
import com.animsh.e_commercetest.data.AuthRepository
import com.animsh.e_commercetest.data.DataStoreRepository
import com.animsh.e_commercetest.ui.auth.login.LoginViewModelFactory
import com.animsh.e_commercetest.ui.auth.signup.SignUpViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by animsh on 4/21/2021.
 */
class TestApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@TestApplication))

        bind() from singleton { AuthRepository() }
        bind() from singleton { DataStoreRepository() }
        bind() from provider { LoginViewModelFactory(instance(), instance()) }
        bind() from provider { SignUpViewModelFactory(instance(), instance()) }
    }

}