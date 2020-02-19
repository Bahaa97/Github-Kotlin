package com.bahaa.github.ui.fragment.offline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.bahaa.github.util.AppTools

class OfflineViewModel : ViewModel() {

    var mutableLiveData: MutableLiveData<Int> = MutableLiveData()


    fun onTryAgain() {
        mutableLiveData.value = AppTools.TRY_AGAIN
    }
}