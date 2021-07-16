package com.example.oompaloompamanager.domain.usecases

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.presentation.models.OompaLoompaViewData
import kotlinx.coroutines.flow.Flow

interface GetWorkers {

    operator fun invoke(page: Int): Flow<AppResponse<List<OompaLoompaViewData>>>
}