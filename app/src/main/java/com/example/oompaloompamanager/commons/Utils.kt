package com.example.oompaloompamanager.commons

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadImage(
    container: View,
    urlImage: String,
    @DrawableRes placeholder: Int?,
    @DrawableRes error: Int?
) {
    Glide.with(container)
        .load(urlImage).apply {
            placeholder?.let { image -> this.placeholder(image) }
            error?.let { image -> this.error(image) }
        }.into(this)
}