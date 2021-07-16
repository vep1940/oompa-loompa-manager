package com.example.oompaloompamanager.data

import com.example.oompaloompamanager.data.models.AdditionalInfoResponse
import com.example.oompaloompamanager.data.models.OompaLoompaDetailResponse
import com.example.oompaloompamanager.data.models.OompaLoompaResponse
import com.example.oompaloompamanager.domain.constants.Gender
import com.example.oompaloompamanager.domain.models.AdditionalInfo
import com.example.oompaloompamanager.domain.models.OompaLoompa
import com.example.oompaloompamanager.domain.models.OompaLoompaDetail

fun OompaLoompaResponse.toDomain(): OompaLoompa{
    return OompaLoompa(
        id,
        firstName,
        lastName,
        gender.toGender(),
        image,
        profession,
        email,
        age,
        height,
        favorite.toDomain()
    )
}

private fun String.toGender(): Gender{
    return when (this){
        "F" -> Gender.FEMALE
        "M" -> Gender.MALE
        else -> Gender.UNDEFINED
    }
}

fun AdditionalInfoResponse.toDomain(): AdditionalInfo{
    return AdditionalInfo(
        color,
        food
    )
}

fun OompaLoompaDetailResponse.toDomain(): OompaLoompaDetail{
    return OompaLoompaDetail(
        first_name,
        last_name,
        gender.toGender(),
        image,
        profession,
        email,
        age,
        height,
        favorite.toDomain()
    )
}