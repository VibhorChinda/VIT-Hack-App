package com.benrostudios.vithackapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.AuthRepository
import com.benrostudios.vithackapp.data.repository.AuthRepositoryImpl
import com.benrostudios.vithackapp.ui.userLogin.UserLoginViewModel
import com.benrostudios.vithackapp.ui.userLogin.UserLoginViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class VitHackApplication: Application() , KodeinAware{

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@VitHackApplication))

        bind<AuthRepository>() with singleton { AuthRepositoryImpl() }
        bind<ViewModelProvider.Factory>() with singleton { UserLoginViewModelFactory(instance()) }

    }


}