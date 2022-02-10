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
class DailyWritingItemViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dailyList = MutableLiveData<List<DailyWritingItem>>()
    val dailyList : LiveData<List<DailyWritingItem>> get() = _dailyList

    private val _weeklyList = MutableLiveData<List<DailyWritingItem>>()
    val weeklyList : LiveData<List<DailyWritingItem>> get() = _weeklyList

    private val _monthlyList = MutableLiveData<List<DailyWritingItem>>()
    val monthlyList : LiveData<List<DailyWritingItem>> get() = _monthlyList

    fun getWritingList(month : Int){
        viewModelScope.launch {
            _dailyList.value = repository.getDailyList(month,"daily")
            _weeklyList.value = repository.getDailyList(month, "weekly")
            _monthlyList.value = repository.getDailyList(month, "monthly")
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}