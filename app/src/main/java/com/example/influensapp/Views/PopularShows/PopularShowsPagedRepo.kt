package com.example.influensapp.Views.PopularShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.Repo.Network
import com.example.influensapp.Data.Repo.PopularShowsDataSource
import com.example.influensapp.Data.Repo.PopularShowsDataSourceFactory
import com.example.influensapp.Data.RestApi.SHOWS_PER_PAGE
import com.example.influensapp.Data.RestApi.RestShowsInterface
import io.reactivex.disposables.CompositeDisposable

class PopularShowsPagedRepo(private val restShowsInterface: RestShowsInterface) {

    lateinit var popularShowsPagedList : LiveData<PagedList<Show>>
    lateinit var popularShowsDataSourceFactory: PopularShowsDataSourceFactory

    fun fetchLiveShowsPage(compositeDisposable: CompositeDisposable):LiveData<PagedList<Show>>{
        popularShowsDataSourceFactory = PopularShowsDataSourceFactory(restShowsInterface,compositeDisposable)
        val config : PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(SHOWS_PER_PAGE)
            .build()
        popularShowsPagedList = LivePagedListBuilder(popularShowsDataSourceFactory,config).build()
        return popularShowsPagedList
    }

    fun getNetworkStatus(): LiveData<Network>{
        return Transformations.switchMap<PopularShowsDataSource, Network>(
            popularShowsDataSourceFactory.popularShowsLiveDataSource,PopularShowsDataSource::networkStatus
        )
    }
}