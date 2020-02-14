package com.example.influensapp.Views.ShowSingle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.Repo.Network
import com.example.influensapp.Data.RestApi.RestShowsClient
import com.example.influensapp.Data.RestApi.RestShowsInterface
import com.example.influensapp.Data.RestApi.SERVER_IMAGES_URL
import com.example.influensapp.R
import kotlinx.android.synthetic.main.activity_show_single.*

class ShowSingleActivity : AppCompatActivity() {
    private lateinit var viewModel: SingleShowViewModel
    private lateinit var showSingleRepo: ShowSingleRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_single)

        var id: Int = intent.getIntExtra("id",1)
        val apirest : RestShowsInterface = RestShowsClient.getClient()
        showSingleRepo = ShowSingleRepo(apirest)
        viewModel = getViewModel(id)
        viewModel.show.observe(this, Observer {
            updateUi(it)
        })
        viewModel.networkStatus.observe(this, Observer {
            if(it == Network.LOADING)
                progressbar_single_show.visibility = View.VISIBLE
            else
                progressbar_single_show.visibility = View.GONE
            if(it == Network.ERROR)
                tv_error_single_show.visibility = View.VISIBLE
            else
                tv_error_single_show.visibility = View.GONE
            if(it == Network.SUCCESS)
                single_show_content.visibility = View.VISIBLE
            else
                single_show_content.visibility = View.GONE
        })
    }

    private fun getViewModel( id: Int) :SingleShowViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleShowViewModel(showSingleRepo,id) as T
            }
        })[SingleShowViewModel::class.java]
    }
    private fun updateUi(show : Show){
        tv_firstdate_popular_show_item.text = show.firstAirDate.toString()
        tv_vote_popular_show_item.text = show.voteAverage.toString()
        single_show_tv_title.text = show.name
        single_show_tv_status.text = show.status
        single_show_tv_overview.text = show.overview
        val imgUrl = SERVER_IMAGES_URL + show.posterPath
        Glide.with(this)
            .load(imgUrl)
            .into(iv_show_single)
    }
}
