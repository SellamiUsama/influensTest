package com.example.influensapp.Data.Repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.RestApi.RestShowsInterface
import io.reactivex.disposables.CompositeDisposable

class PopularShowsDataSourceFactory(private val restShowsInterface: RestShowsInterface, private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int,Show>() {

    val popularShowsLiveDataSource = MutableLiveData<PopularShowsDataSource>()

    override fun create(): DataSource<Int, Show> {
        val popularShowsDataSource = PopularShowsDataSource(restShowsInterface,compositeDisposable)
        popularShowsLiveDataSource.postValue(popularShowsDataSource)
        return popularShowsDataSource
    }
}