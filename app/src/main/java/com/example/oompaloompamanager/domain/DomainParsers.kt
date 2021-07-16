package com.example.oompaloompamanager.domain

import com.example.oompaloompamanager.domain.models.OompaLoompa
import com.example.oompaloompamanager.domain.models.OompaLoompaDetail
import com.example.oompaloompamanager.presentation.models.OompaLoompaDetailViewData
import com.example.oompaloompamanager.presentation.models.OompaLoompaViewData

fun OompaLoompa.toViewData(): OompaLoompaViewData{
    return OompaLoompaViewData(
        id,
        firstName,
        lastName,
        gender.value,
        image,
        profession,
        email,
        age.toString(),
        height.toString(),
        additionalInfo.favoriteColor,
        additionalInfo.favoriteFood
    )
}

fun OompaLoompaDetail.toViewData(): OompaLoompaDetailViewData{
    return OompaLoompaDetailViewData(
        firstName,
        lastName,
        gender.value,
        image,
        profession,
        email,
        age.toString(),
        height.toString(),
        additionalInfo.favoriteColor,
        additionalInfo.favoriteFood
    )
}