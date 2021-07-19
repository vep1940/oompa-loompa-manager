package com.example.oompaloompamanager.domain.usecases

import com.example.oompaloompamanager.domain.repositories.WorkerRepository

import javax.inject.Inject

class GetWorkerDetail @Inject constructor(private val workerRepository: WorkerRepository) {

    operator fun invoke(id: Int) = workerRepository.getWorkerDetail(id)

}