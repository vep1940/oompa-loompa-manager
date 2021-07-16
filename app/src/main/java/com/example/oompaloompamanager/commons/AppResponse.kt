package com.example.oompaloompamanager.commons

import com.example.oompaloompamanager.domain.constants.AppError

sealed class AppResponse<out T> {

    object Loading : AppResponse<Nothing>()
    class ResponseOk<T>(val value: T) : AppResponse<T>()
    class ResponseKo(val error: AppError) : AppResponse<Nothing>()
}
