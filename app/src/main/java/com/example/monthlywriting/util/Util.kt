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
import java.text.DateFormatSymbols
import java.time.LocalDate
import java.util.*

@BindingAdapter("imgBitmap")
fun loadImage(imageView: ImageView, bitmap: Bitmap?){
    Glide.with(imageView.context)
        .load(bitmap)
        .error(R.drawable.photo_black)
        .placeholder(R.drawable.photo_black)
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

class CurrentInfo {

    companion object{
        val date : LocalDate = LocalDate.now()

        val currentYear : Int = date.year
        val currentMonth : Int = date.monthValue
        val currentDate : Int = date.dayOfMonth

        val currentMonthName : String = date.month.toString()
        val currentMonthShortName : String = DateFormatSymbols().shortMonths[currentMonth-1]

        fun getCurrentEndDateOfMonth() : Int {
            val cal = Calendar.getInstance()
            cal.set(Companion.currentYear, Companion.currentMonth - 1, currentDate)
            return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

    }


}