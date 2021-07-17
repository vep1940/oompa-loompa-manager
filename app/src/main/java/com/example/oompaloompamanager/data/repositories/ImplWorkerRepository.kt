package com.example.oompaloompamanager.data.repositories

import com.example.oompaloompamanager.data.datasources.DataSource
import com.example.oompaloompamanager.data.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImplWorkerRepository @Inject constructor(
    private val localDatasource: DataSource.Local,
    private val networkDatasource: DataSource.Network,
) : WorkerRepository, BaseRepository() {

    override fun getWorkers(page: Int) = flow {

        val response = manageRequest(
            { localDatasource.getWorkers(page) },
            { networkResponse -> localDatasource.setWorkers(page, networkResponse) },
            { networkDatasource.getWorkers(page) },
            { response -> response.value.results.map { result -> result.toDomain() } }
        )

        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getWorkerPages(): Int? = localDatasource.getWorkerPages()

    override fun getWorkerDetail(id: Int) = flow {

        val response = manageRequest(
            { localDatasource.getWorkerDetail(id) },
            { networkResponse -> localDatasource.setWorkerDetail(id, networkResponse) },
            { networkDatasource.getWorkerDetail(id) },
            { response -> response.value.toDomain() }
        )

        emit(response)
    }.flowOn(Dispatchers.IO)
}