package com.example.oompaloompamanager.domain.usecases

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.presentation.models.OompaLoompaDetailViewData
import kotlinx.coroutines.flow.Flow

interface GetWorkerDetail {

    operator fun invoke(id: Int): Flow<AppResponse<OompaLoompaDetailViewData>>
}