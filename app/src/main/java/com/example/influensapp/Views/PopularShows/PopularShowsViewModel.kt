package com.example.influensapp.Views.PopularShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.Repo.Network
import io.reactivex.disposables.CompositeDisposable

class PopularShowsViewModel(private val popularShowsPagedRepo: PopularShowsPagedRepo): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val popularShowsPagedList : LiveData<PagedList<Show>>  = popularShowsPagedRepo.fetchLiveShowsPage(compositeDisposable)
    val networkStatus : LiveData<Network> =  popularShowsPagedRepo.getNetworkStatus()

    fun listIsEmpty(): Boolean{
        return popularShowsPagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}