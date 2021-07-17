package com.example.oompaloompamanager.domain.constants

enum class AppError (val code: Int, val message: String) {
    UNSUCCESSFUL_REQUEST_ERROR(10, ""),
    EMPTY_BODY_RESPONSE_ERROR(11, ""),
    NO_INTERNET_ERROR(12, ""),
    HTTP_ERROR(13, ""),
    PARSING_ERROR(14, ""),
    NO_MORE_PAGES_ERROR(15, ""),
    UNKNOWN_ERROR(100, "")
}