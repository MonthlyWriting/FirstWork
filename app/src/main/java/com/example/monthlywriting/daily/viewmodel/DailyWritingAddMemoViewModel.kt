package com.example.monthlywriting.daily.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monthlywriting.Repository
import javax.inject.Inject

class DailyWritingAddMemoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    var content = MutableLiveData<String>()
    var photo = MutableLiveData<Bitmap>()
}