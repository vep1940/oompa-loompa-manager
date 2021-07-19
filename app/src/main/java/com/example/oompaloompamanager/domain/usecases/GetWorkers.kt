package com.example.oompaloompamanager.domain.usecases

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.domain.repositories.WorkerRepository
import com.example.oompaloompamanager.domain.constants.AppError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWorkers @Inject constructor(private val workerRepository: WorkerRepository) {

    operator fun invoke(page: Int) = flow {
        if (workerRepository.getWorkerPages() != null && workerRepository.getWorkerPages()!! < page) {
            emit(AppResponse.ResponseKo(AppError.NO_MORE_PAGES_ERROR))
        } else {
            workerRepository.getWorkers(page).collect { response -> emit(response) }
        }
    }.flowOn(Dispatchers.IO)
}