package com.example.monthlywriting.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.monthlywriting.R
import java.text.DateFormatSymbols
import java.time.LocalDate
import java.util.*

@BindingAdapter("imgBitmap")
fun loadImage(imageView: ImageView, bitmap: Bitmap?) {
    Glide.with(imageView.context)
        .load(bitmap)
        .into(imageView)
}

fun checkPermission(context: Context, execute: () -> Unit) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_DENIED
    ) {
        ActivityCompat.requestPermissions(
            (context as ContextWrapper).baseContext as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            10
        )
    } else {
        execute()
    }
}

class CurrentInfo {

    companion object {
        val date: LocalDate = LocalDate.now()

        val currentYear: Int = date.year
        val currentMonth: Int = date.monthValue
        val currentDate: Int = date.dayOfMonth

        val currentMonthName: String = date.month.toString()
        val currentMonthShortName: String = DateFormatSymbols().shortMonths[currentMonth - 1]

        val monthShortNames: Array<String> = DateFormatSymbols().shortMonths

        fun getCurrentEndDateOfMonth(): Int {
            val cal = Calendar.getInstance()
            cal.set(currentYear, currentMonth - 1, currentDate)
            return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        val backgroundList = mutableListOf(
            R.drawable.photo_jan,
            R.drawable.photo_feb,
            R.drawable.photo_mar,
            R.drawable.photo_apr,
            R.drawable.photo_may,
            R.drawable.photo_jun,
            R.drawable.photo_jul,
            R.drawable.photo_aug,
            R.drawable.photo_sep,
            R.drawable.photo_oct,
            R.drawable.photo_nov,
            R.drawable.photo_dec,
        )
    }
}

fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
    try {
        if (source.height >= source.width) {
            if (source.height <= maxLength) { // if image height already smaller than the required height
                return source
            }

            val aspectRatio = source.width.toDouble() / source.height.toDouble()
            val targetWidth = (maxLength * aspectRatio).toInt()
            val result = Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
            return result
        } else {
            if (source.width <= maxLength) { // if image width already smaller than the required width
                return source
            }

            val aspectRatio = source.height.toDouble() / source.width.toDouble()
            val targetHeight = (maxLength * aspectRatio).toInt()

            val result = Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
            return result
        }
    } catch (e: Exception) {
        return source
    }
}

fun Any.toast(context: Context): Toast {
    return Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).apply { show() }
}