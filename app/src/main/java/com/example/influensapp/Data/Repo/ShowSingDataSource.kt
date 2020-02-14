package com.example.influensapp.Data.Repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.RestApi.RestShowsInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class ShowSingDataSource(private val apiRest : RestShowsInterface, private val compositeDisposable: CompositeDisposable) {
    private val myNetworkState = MutableLiveData<Network>()
    val networkState : LiveData<Network>
    get() = myNetworkState

    private val myShowSingleResponse = MutableLiveData<Show>()
    val showSingleResponse : LiveData<Show>
    get() = myShowSingleResponse

    fun fetchShowSingle(id : Int){
        myNetworkState.postValue(Network.LOADING)

        try {
            compositeDisposable.add(
                apiRest.getShow(id)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        myShowSingleResponse.postValue(it)
                        myNetworkState.postValue(Network.SUCCESS)
                    },{
                        myNetworkState.postValue(Network.ERROR)
                        Log.e("ShowSingleApiDS",it.message.toString())
                    })
            )
        }catch (e: Exception){
            Log.e("ShowSingleApiDS",e.message.toString())

        }
    }

}