package com.example.android.architecture.blueprints.todoapp.di

import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTaskRepository(impl: DefaultTasksRepository): TasksRepository

    @Singleton
    @Binds
    @LocalDataSource
    abstract fun bindTasksLocalDataSource(impl: TasksLocalDataSource): TasksDataSource

    @Singleton
    @Binds
    @RemoteDataSource
    abstract fun bindTasksRemoteDataSource(impl: TasksRemoteDataSource): TasksDataSource
}

@Qualifier
annotation class LocalDataSource

@Qualifier
annotation class RemoteDataSource