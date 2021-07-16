package com.example.oompaloompamanager.data.datasources.network.services

import com.example.oompaloompamanager.data.models.OompaLoompaDetailResponse
import com.example.oompaloompamanager.data.models.WorkerPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("oompa-loompas")
    suspend fun getWorkers(@Query("page") page: Int): Response<WorkerPageResponse>

    @GET("oompa-loompas/{id}")
    suspend fun getWorker(@Path("id") id: Int): Response<OompaLoompaDetailResponse>
}