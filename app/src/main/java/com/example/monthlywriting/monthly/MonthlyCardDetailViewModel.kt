package com.example.monthlywriting.monthly

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monthlywriting.Repository
import com.example.monthlywriting.model.DailyWritingItem
import com.example.monthlywriting.model.MonthlyWritingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyCardDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dailyList = MutableLiveData<List<DailyWritingItem>>()
    val dailyList: LiveData<List<DailyWritingItem>> get() = _dailyList

    private val _monthlyList = MutableLiveData<List<MonthlyWritingItem>>()
    val monthlyList: LiveData<List<MonthlyWritingItem>> get() = _monthlyList

    fun getAllItems(month: Int) {
        viewModelScope.launch {
            _dailyList.value = repository.getAllDailyList(month).sortedWith(
                compareBy({ it.type == "monthly" },
                    { it.type == "weekly" },
                    { it.type == "daily" })
            )
            _monthlyList.value = repository.getAllMonthlyList(month)
        }
    }
}