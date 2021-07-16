package com.example.oompaloompamanager.di

import com.example.oompaloompamanager.data.datasources.DataSource
import com.example.oompaloompamanager.data.datasources.local.ImplLocalDatasource
import com.example.oompaloompamanager.data.datasources.network.ImplNetworkDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DatasourcesBinder {

    @Binds
    fun bindsNetworkDatasource(networkDatasource: ImplNetworkDatasource): DataSource.Network

    @Binds
    fun bindsLocalDatasource(localDatasource: ImplLocalDatasource): DataSource.Local
}