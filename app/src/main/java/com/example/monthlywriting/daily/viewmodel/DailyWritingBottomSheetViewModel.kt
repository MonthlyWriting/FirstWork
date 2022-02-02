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
class DailyWritingBottomSheetViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){

    private val _currentItem = MutableLiveData<DailyWritingItem>()
    val currentItem : LiveData<DailyWritingItem> get() = _currentItem

    fun getCurrentItem(id: Int) {
        viewModelScope.launch {
            _currentItem.value = repository.getSingleItem(id)
        }
    }
}