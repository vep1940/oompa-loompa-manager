package com.example.oompaloompamanager.data.models

data class OompaLoompaResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val favorite: AdditionalInfoResponse,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val height: Int
)