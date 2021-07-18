package com.example.oompaloompamanager.domain.constants

enum class AppError (val code: Int, val message: String) {
    UNSUCCESSFUL_REQUEST_ERROR(10, "Unsuccessful request"),
    EMPTY_BODY_RESPONSE_ERROR(11, "Empty body response"),
    NO_INTERNET_ERROR(12, "No internet"),
    HTTP_ERROR(13, "Http error"),
    PARSING_ERROR(14, "Parsing error"),
    NO_MORE_PAGES_ERROR(15, "No more pages to retrieve"),
    UNKNOWN_ERROR(100, "Unknown error")
}