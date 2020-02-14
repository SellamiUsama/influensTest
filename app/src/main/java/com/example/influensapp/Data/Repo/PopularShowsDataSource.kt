package com.example.influensapp.Data.Repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.RestApi.INITIAL_PAGE
import com.example.influensapp.Data.RestApi.RestShowsInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularShowsDataSource(private val restShowsInterface: RestShowsInterface, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int,Show>() {

    private var page = INITIAL_PAGE
    val networkStatus : MutableLiveData<Network> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Show>
    ) {
        networkStatus.postValue(Network.LOADING)
        compositeDisposable.add(
            restShowsInterface.getPopularShows(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.results,null,page+1)
                    networkStatus.postValue(Network.SUCCESS)
                },{
                    networkStatus.postValue(Network.ERROR)
                    Log.e("popular datasource init",it.message.toString())
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Show>) {
        networkStatus.postValue(Network.LOADING)
        compositeDisposable.add(
            restShowsInterface.getPopularShows(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if(it.totalPages >= params.key){
                        callback.onResult(it.results, params.key + 1)
                        networkStatus.postValue(Network.SUCCESS)
                    }else{
                        networkStatus.postValue(Network.NO_MORE_SHOWS)
                    }

                },{
                    networkStatus.postValue(Network.ERROR)
                    Log.e("popular datasource",it.message.toString())
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Show>) {
    }
}