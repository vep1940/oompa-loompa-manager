package com.example.oompaloompamanager.domain.usecases

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.data.repositories.WorkerRepository
import com.example.oompaloompamanager.domain.toViewData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImplGetWorkerDetail @Inject constructor(private val workerRepository: WorkerRepository) :
    GetWorkerDetail {

    override operator fun invoke(id: Int) = flow {
        workerRepository.getWorkerDetail(id).collect { response ->
            when (response) {
                is AppResponse.ResponseOk -> {
                    emit(
                        AppResponse.ResponseOk(
                            response.value.toViewData()
                        )
                    )
                }
                is AppResponse.ResponseKo -> emit(response)
                is AppResponse.Loading -> {}
            }
        }
    }
}