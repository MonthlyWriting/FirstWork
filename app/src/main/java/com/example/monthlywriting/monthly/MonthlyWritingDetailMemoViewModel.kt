package com.example.monthlywriting.monthly

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
class MonthlyWritingDetailMemoViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _item = MutableLiveData<DailyWritingItem>()
    val item: LiveData<DailyWritingItem> get() = _item

    fun getItem(id: Int){
        viewModelScope.launch {
            _item.value = repository.getSingleItem(id)
        }
    }
}