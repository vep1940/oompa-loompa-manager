package com.example.oompaloompamanager.di

import com.example.oompaloompamanager.domain.usecases.GetWorkerDetail
import com.example.oompaloompamanager.domain.usecases.GetWorkers
import com.example.oompaloompamanager.domain.usecases.ImplGetWorkerDetail
import com.example.oompaloompamanager.domain.usecases.ImplGetWorkers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UsecasesBinder {

    @Binds
    fun bindsGetWorkers(getWorkers: ImplGetWorkers): GetWorkers

    @Binds
    fun bindsGetWorkerDetail(getWorkerDetail: ImplGetWorkerDetail): GetWorkerDetail
}