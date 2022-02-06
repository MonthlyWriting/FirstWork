package com.example.monthlywriting.daily.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monthlywriting.Repository
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.model.DailyWritingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.function.Predicate
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

    fun saveNewMemo(id: Int, memo : DailyMemo) {
        viewModelScope.launch {
            val item = _currentItem.value!!
            item.dailymemo.add(memo)
            item.dailymemo.sortBy { it.date }

            repository.updateDailyMemo(id, item.dailymemo)
            _currentItem.value = item
        }
    }

    fun updateMemo(id: Int, memo : DailyMemo) {
        viewModelScope.launch {
            val item = _currentItem.value!!
            item.dailymemo.forEachIndexed { index, it ->
                if (it.date == memo.date) {
                    item.dailymemo.removeAt(index)
                }
            }
            item.dailymemo.add(memo)
            item.dailymemo.sortBy { it.date }

            repository.updateDailyMemo(id, item.dailymemo)
            _currentItem.value = item
        }
    }

    fun setItemDone(date : Int, boolean: Boolean){
        viewModelScope.launch {
            val doneList = _currentItem.value?.done!!
            doneList[date] = boolean

            repository.updateDone(currentItem.value?.id!!, doneList)
            _currentItem.value?.done = doneList
        }
    }
}