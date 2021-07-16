package com.example.oompaloompamanager.data.datasources

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.data.models.OompaLoompaDetailResponse
import com.example.oompaloompamanager.data.models.OompaLoompaResponse
import com.example.oompaloompamanager.data.models.WorkerPageResponse

interface DataSource {

    interface Network {
        suspend fun getWorkers(page: Int): AppResponse<WorkerPageResponse>
        suspend fun getWorkerDetail(id: Int): AppResponse<OompaLoompaDetailResponse>
    }

    interface Local {
        fun getWorkers(page: Int): AppResponse<WorkerPageResponse?>
        fun setWorkers(page: Int, workerList: WorkerPageResponse)
        fun getWorkerDetail(id: Int): AppResponse<OompaLoompaDetailResponse?>
        fun setWorkerDetail(id: Int, workerDetail: OompaLoompaDetailResponse)
    }
}