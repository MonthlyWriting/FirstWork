package com.example.monthlywriting.daily.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyWritingDetailMemoViewModel : ViewModel(){

    var date = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    var photo = MutableLiveData<Bitmap>()
}