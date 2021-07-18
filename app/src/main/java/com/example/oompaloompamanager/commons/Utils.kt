package com.example.oompaloompamanager.commons

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.oompaloompamanager.R

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

fun View.slideInTop() {
    val animation = AnimationUtils.loadAnimation(this.context, R.anim.slide_in_top)
    this.visibility = View.VISIBLE
    this.startAnimation(animation)
}

fun View.slideOutTop() {
    val animation = AnimationUtils.loadAnimation(this.context, R.anim.slide_out_top).apply {
        setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                this@slideOutTop.visibility = View.GONE
            }
        })
    }
    this.startAnimation(animation)
}