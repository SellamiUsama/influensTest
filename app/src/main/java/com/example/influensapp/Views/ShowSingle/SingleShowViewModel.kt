package com.example.influensapp.Views.ShowSingle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.influensapp.Data.Entities.Show.Show
import io.reactivex.disposables.CompositeDisposable

class SingleShowViewModel (private val showSingleRepo: ShowSingleRepo, id : Int) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val show : LiveData<Show> = showSingleRepo.fetchShowSingle(compositeDisposable,id)
    val networkStatus = showSingleRepo.getShowSingNetworkStatus()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}