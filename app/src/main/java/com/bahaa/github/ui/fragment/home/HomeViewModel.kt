package com.bahaa.github.ui.fragment.home

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.bahaa.github.data.dataBase.RepoRoomDatabase
import com.bahaa.github.data.network.ConnectivityStatus
import com.bahaa.github.data.network.RetrofitClient
import com.bahaa.github.models.RepoModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val application: Application) : ViewModel() {

    internal var moviesResponseMutableLiveData: MutableLiveData<List<RepoModel>>
    internal var page: MutableLiveData<Int>
    internal var items: MutableLiveData<Int>
    internal var isLastPage = false
    internal var isLoading = false
    private var database: RepoRoomDatabase? = null

    init {
        moviesResponseMutableLiveData = MutableLiveData()
        page = MutableLiveData()
        items = MutableLiveData()
        database = RepoRoomDatabase.getDatabase(application)
        page.value = 1
        items.value = 15
        getData()
    }

    internal fun getData() {
        if (!ConnectivityStatus.isConnected(application)) {
            getDataFromDataBase()
        } else {
            getDataFromApi()
        }
    }

    @SuppressLint("CheckResult")
    internal fun getDataFromApi() {
        RetrofitClient.webService().getData(page.value!!, items.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    isLoading = false
                    if (result.size > 0) {
                        if (moviesResponseMutableLiveData.value != null && moviesResponseMutableLiveData.value!!.size > 0) {
                            moviesResponseMutableLiveData.postValue(result)
                            addData(result)
                        } else {
                            moviesResponseMutableLiveData.postValue(result)
                            addData(result)
                        }

                    } else {
                        isLastPage = true
                    }
                }, { throwable -> Log.e("NETWORK ERROR", throwable.message) })
    }

    internal fun addData(repoModel: List<RepoModel>) {
        Thread(Runnable { database?.RepoDeo()?.insert(repoModel) }).start()
    }

    internal fun getDataFromDataBase() {
        Thread(Runnable {
            moviesResponseMutableLiveData = MutableLiveData()
            moviesResponseMutableLiveData.postValue(database?.RepoDeo()?.repos)
        }).start()

    }


    override fun onCleared() {
        super.onCleared()
    }


}