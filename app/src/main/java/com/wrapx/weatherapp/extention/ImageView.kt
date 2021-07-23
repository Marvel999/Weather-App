package com.wrapx.weatherapp.extention

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(drawable: Drawable) {
    Glide.with(this)
        .load(drawable)
        .into(this)
}