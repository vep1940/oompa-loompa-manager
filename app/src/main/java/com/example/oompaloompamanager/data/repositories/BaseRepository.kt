package com.example.oompaloompamanager.data.repositories

import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.domain.constants.AppError

abstract class BaseRepository {

    suspend fun <T, R> manageRequest(
        localRequest: () -> AppResponse<T?>,
        saveLocalRequest: (T) -> Unit,
        networkRequest: suspend () -> AppResponse<T>,
        parsingLambda: (AppResponse.ResponseOk<T>) -> R
    ): AppResponse<R> {
        val response = when (val localResponse = localRequest()) {
            is AppResponse.ResponseOk -> {
                localResponse.value?.let {
                    localResponse as AppResponse.ResponseOk<T>
                } ?: let {
                    when (val networkResponse = networkRequest()) {
                        is AppResponse.ResponseOk -> {
                            saveLocalRequest(networkResponse.value)
                            networkResponse
                        }
                        is AppResponse.ResponseKo -> networkResponse
                        is AppResponse.Loading -> networkResponse
                    }
                }
            }
            is AppResponse.ResponseKo -> localResponse
            is AppResponse.Loading -> localResponse
        }

        return when (response) {
            is AppResponse.ResponseOk -> {
                try {
                    AppResponse.ResponseOk(parsingLambda(response))
                } catch (ex: Exception) {
                    AppResponse.ResponseKo(AppError.PARSING_ERROR)
                }
            }
            is AppResponse.ResponseKo -> response
            is AppResponse.Loading -> response
        }
    }
}