package com.example.influensapp.Views.PopularShows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.influensapp.Data.Repo.Network
import com.example.influensapp.Data.RestApi.RestShowsClient
import com.example.influensapp.Data.RestApi.RestShowsInterface
import com.example.influensapp.R
import kotlinx.android.synthetic.main.activity_popular_shows.*

class PopularShowsActivity : AppCompatActivity() {

    private lateinit var viewModel: PopularShowsViewModel
    lateinit var popularShowsPagedRepo: PopularShowsPagedRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_shows)

        val restapi : RestShowsInterface = RestShowsClient.getClient()
        popularShowsPagedRepo = PopularShowsPagedRepo(restapi)
        viewModel = getViewModel()
        val popularShowAdapter = PopularShowPagedAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType : Int = popularShowAdapter.getItemViewType(position)
                if (viewType == popularShowAdapter.SHOW_SHOW) return 1
                else
                    return 2
            }
        }

        rv_popular_shows.layoutManager = gridLayoutManager
        rv_popular_shows.setHasFixedSize(true)
        rv_popular_shows.adapter = popularShowAdapter

        viewModel.popularShowsPagedList.observe(this, Observer {
            popularShowAdapter.submitList(it)
        })
        viewModel.networkStatus.observe(this , Observer {
            if(it == Network.LOADING)
            progressbar_popular_shows.visibility = View.VISIBLE
            else
            progressbar_popular_shows.visibility = View.GONE
            if(it == Network.ERROR)
                tv_error_popular_shows.visibility = View.VISIBLE
            else
                tv_error_popular_shows.visibility = View.GONE
            if (viewModel.listIsEmpty()){
                popularShowAdapter.setNetworkStatus(it)
            }


        })
    }

    private fun getViewModel() : PopularShowsViewModel{
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PopularShowsViewModel(popularShowsPagedRepo) as T
            }
        })[PopularShowsViewModel::class.java]
    }
}
