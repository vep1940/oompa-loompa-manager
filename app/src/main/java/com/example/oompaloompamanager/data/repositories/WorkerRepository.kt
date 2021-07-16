package com.example.oompaloompamanager.data.repositories

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.domain.models.OompaLoompa
import com.example.oompaloompamanager.domain.models.OompaLoompaDetail
import kotlinx.coroutines.flow.Flow

interface WorkerRepository {

    fun getWorkers(page: Int): Flow<AppResponse<List<OompaLoompa>>>

    fun getWorkerDetail(id: Int): Flow<AppResponse<OompaLoompaDetail>>
}