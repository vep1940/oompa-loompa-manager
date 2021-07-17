package com.example.oompaloompamanager.domain.usecases

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.data.repositories.WorkerRepository
import com.example.oompaloompamanager.domain.constants.AppError
import com.example.oompaloompamanager.domain.toViewData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImplGetWorkers @Inject constructor(private val workerRepository: WorkerRepository) :
    GetWorkers {

    override operator fun invoke(page: Int) = flow {
        if (workerRepository.getWorkerPages() != null && workerRepository.getWorkerPages()!! < page) {
            emit(AppResponse.ResponseKo(AppError.NO_MORE_PAGES_ERROR))
        } else {
            workerRepository.getWorkers(page).collect { response ->
                when (response) {
                    is AppResponse.ResponseOk -> {
                        emit(
                            AppResponse.ResponseOk(
                                response.value.map { worker -> worker.toViewData() }
                            )
                        )
                    }
                    is AppResponse.ResponseKo -> emit(response)
                    is AppResponse.Loading -> {
                    }
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}