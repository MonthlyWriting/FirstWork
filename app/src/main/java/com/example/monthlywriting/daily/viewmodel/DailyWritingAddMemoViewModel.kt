package com.example.monthlywriting.daily.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class DailyWritingAddMemoViewModel : ViewModel() {

    var date = MutableLiveData(
        SimpleDateFormat("dd", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()
    )
    var content = MutableLiveData<String>()
    var photo = MutableLiveData<Bitmap>(null)
}