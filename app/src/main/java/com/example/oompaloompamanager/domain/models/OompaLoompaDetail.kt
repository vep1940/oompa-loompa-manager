package com.example.oompaloompamanager.domain.models

import com.example.oompaloompamanager.domain.constants.Gender

data class OompaLoompaDetail(
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val height: Int,
    val quota: String,
    val additionalInfo: AdditionalInfo
)