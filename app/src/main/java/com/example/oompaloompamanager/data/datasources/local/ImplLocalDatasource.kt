package com.example.oompaloompamanager.data.datasources.local

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.data.datasources.DataSource
import com.example.oompaloompamanager.data.models.OompaLoompaDetailResponse
import com.example.oompaloompamanager.data.models.WorkerPageResponse
import javax.inject.Inject

class ImplLocalDatasource @Inject constructor() : DataSource.Local {

    companion object {
        private var totalWorkerPages: Int? = null
        private val cacheOompaLoompaResponse: HashMap<Int, WorkerPageResponse> =
            hashMapOf()
        private val cacheOompaLoompaDetailResponse: HashMap<Int, OompaLoompaDetailResponse> =
            hashMapOf()
    }

    override fun getWorkerPages(): Int? = totalWorkerPages

    override fun getWorkers(page: Int) = AppResponse.ResponseOk(cacheOompaLoompaResponse[page])

    override fun setWorkers(page: Int, workerList: WorkerPageResponse) {
        if (totalWorkerPages == null){
            totalWorkerPages = workerList.total
        }
        cacheOompaLoompaResponse[page] = workerList
    }

    override fun getWorkerDetail(id: Int) = AppResponse.ResponseOk(cacheOompaLoompaDetailResponse[id])

    override fun setWorkerDetail(id: Int, workerDetail: OompaLoompaDetailResponse) {
        cacheOompaLoompaDetailResponse[id] = workerDetail
    }
}