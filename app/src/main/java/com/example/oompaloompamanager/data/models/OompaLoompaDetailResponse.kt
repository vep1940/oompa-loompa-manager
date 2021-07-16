package com.example.oompaloompamanager.data.models

data class OompaLoompaDetailResponse(
    val first_name: String,
    val last_name: String,
    val description: String,
    val image: String,
    val profession: String,
    val quota: String,
    val height: Int,
    val country: String,
    val age: Int,
    val favorite: AdditionalInfoResponse,
    val gender: String,
    val email: String
)