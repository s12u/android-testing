package com.example.android.architecture.blueprints.todoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@InstallIn(ApplicationComponent::class)
@Module
class DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun bindDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun bindIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun bindMainDispatcher(): CoroutineDispatcher = Dispatchers.Main


}

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class IoDispatcher

@Qualifier
annotation class MainDispatcher