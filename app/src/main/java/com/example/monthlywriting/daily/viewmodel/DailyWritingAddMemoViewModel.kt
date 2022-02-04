package com.example.monthlywriting.daily.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monthlywriting.Repository
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.model.DailyWritingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DailyWritingAddMemoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    var date = MutableLiveData(
        SimpleDateFormat("dd", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()
    )
    var content = MutableLiveData<String>()
    var photo = MutableLiveData<Bitmap>()

}