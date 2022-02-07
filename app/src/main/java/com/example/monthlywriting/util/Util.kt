package com.example.monthlywriting.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.monthlywriting.R

@BindingAdapter("imgBitmap")
fun loadImage(imageView: ImageView, bitmap: Bitmap?){
    Glide.with(imageView.context)
        .load(bitmap)
        .error(R.drawable.ic_baseline_photo_library_24)
        .placeholder(R.drawable.ic_baseline_photo_library_24)
        .into(imageView)
}

fun checkPermission(context: Context, execute : () -> Unit) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                (context as ContextWrapper).baseContext as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                10)
    } else {
        execute()
    }
}