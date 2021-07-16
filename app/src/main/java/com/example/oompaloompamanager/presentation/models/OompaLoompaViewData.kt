package com.example.oompaloompamanager.presentation.models

import com.example.oompaloompamanager.domain.constants.Gender

data class OompaLoompaViewData(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: String,
    val height: String,
    val favoriteColor: String,
    val favoriteFood: String
)