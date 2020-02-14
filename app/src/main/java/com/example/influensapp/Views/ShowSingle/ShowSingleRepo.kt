package com.example.influensapp.Views.ShowSingle

import androidx.lifecycle.LiveData
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.Repo.ShowSingDataSource
import com.example.influensapp.Data.Repo.Network
import com.example.influensapp.Data.RestApi.RestShowsInterface
import io.reactivex.disposables.CompositeDisposable

class ShowSingleRepo(private val apiRest : RestShowsInterface) {
    lateinit var showSingDataSource: ShowSingDataSource

    fun fetchShowSingle(compositeDisposable: CompositeDisposable, id : Int):LiveData<Show>{
        showSingDataSource = ShowSingDataSource(apiRest,compositeDisposable)
        showSingDataSource.fetchShowSingle(id)
        return showSingDataSource.showSingleResponse
    }

    fun getShowSingNetworkStatus():LiveData<Network>{
        return showSingDataSource.networkState
    }
}