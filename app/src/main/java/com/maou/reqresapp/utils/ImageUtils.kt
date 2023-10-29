package com.maou.reqresapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun bindImage(ctx: Context, imageSource: String, view: ImageView) {
    Glide.with(ctx).load(imageSource).into(view)
}