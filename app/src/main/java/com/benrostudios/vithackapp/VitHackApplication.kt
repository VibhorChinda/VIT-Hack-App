package com.benrostudios.vithackapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.*
import com.benrostudios.vithackapp.ui.auth.userPhone.UserPhoneViewModelFactory
import com.benrostudios.vithackapp.ui.auth.userSignIn.UserSignInViewModelFactory
import com.benrostudios.vithackapp.ui.auth.userSignUp.UserSignUpViewModelFactory
import com.benrostudios.vithackapp.ui.auth.welcome.WelcomeViewModelFactory
import com.benrostudios.vithackapp.ui.home.faq.FaqViewModelFactory
import com.benrostudios.vithackapp.ui.home.timeline.TimelineViewModelFactory
import com.benrostudios.vithackapp.ui.splash.SplashActivityViewModelFactory
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class VitHackApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@VitHackApplication))
        bind<AuthRepository>() with singleton { AuthRepositoryImpl() }
        bind<TimelineRepository>() with singleton { TimelineRepositoryImpl() }
        bind<UserOperationRepository>() with singleton { UserOperationRepositoryImpl() }
        bind<FaqRepository>() with singleton { FaqRepositoryImpl() }
        bind() from provider { UserSignInViewModelFactory(instance()) }
        bind() from provider { TimelineViewModelFactory(instance()) }
        bind() from provider { SharedPrefUtils(instance()) }
        bind() from provider { UserSignUpViewModelFactory(instance()) }
        bind() from provider { WelcomeViewModelFactory(instance(),instance()) }
        bind() from provider { UserPhoneViewModelFactory(instance()) }
        bind() from provider { FaqViewModelFactory(instance()) }
        bind() from provider { SplashActivityViewModelFactory(instance()) }
    }


}