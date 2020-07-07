package com.leeloo.esist.di

import com.leeloo.esist.ui.MainActivity
import com.leeloo.esist.ui.nav.Coordinator
import com.leeloo.esist.ui.nav.CoordinatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ContextComponent {

    @Singleton
    @Provides
    fun provideCoordinator(activity: MainActivity): Coordinator =
        CoordinatorImpl(activity.supportFragmentManager)

}