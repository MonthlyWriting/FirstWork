package com.example.monthlywriting.daily.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyWritingAddViewModel : ViewModel() {

    var name = MutableLiveData<String>()
    var timesAWeek = MutableLiveData(1)
    var timesAMonth = MutableLiveData(1)

    private val _test = MutableLiveData<String>()
    val test : LiveData<String> get() = _test

}