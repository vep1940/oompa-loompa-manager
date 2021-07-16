package com.example.oompaloompamanager.di

import com.example.oompaloompamanager.data.repositories.ImplWorkerRepository
import com.example.oompaloompamanager.data.repositories.WorkerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesBinder {

    @Binds
    fun bindsWorkerRepository(workerRepository: ImplWorkerRepository): WorkerRepository
}