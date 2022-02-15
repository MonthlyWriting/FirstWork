package com.example.monthlywriting.daily.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monthlywriting.Repository
import com.example.monthlywriting.model.DailyWritingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyWritingAddViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var name = MutableLiveData<String>()
    var timesAWeek = MutableLiveData(1)
    var timesAMonth = MutableLiveData(1)

    fun insertNewItem(dailyWritingItem: DailyWritingItem) {
        viewModelScope.launch {
            repository.insert(dailyWritingItem)
        }
    }
}