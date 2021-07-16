package com.example.oompaloompamanager.data.models

data class WorkerPageResponse(
    val current: Int,
    val total: Int,
    val results: List<OompaLoompaResponse>
)