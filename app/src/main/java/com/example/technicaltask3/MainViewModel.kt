package com.example.technicaltask3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val mutableLiveDataModelResult: MutableLiveData<MutableList<SlidePageFragment>> = MutableLiveData(mutableListOf(SlidePageFragment()))
    val liveDataModelResult: LiveData<MutableList<SlidePageFragment>> = mutableLiveDataModelResult

    fun addFragment(){
        mutableLiveDataModelResult.run {
            var list = mutableLiveDataModelResult.value
            list?.add(SlidePageFragment())
            this.value = list
        }
    }

    fun removeFragment(){
        mutableLiveDataModelResult.run {
            var list = mutableLiveDataModelResult.value
            list?.removeLast()
            this.value = list
        }
    }
}