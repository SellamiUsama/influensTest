package com.example.influensapp.Views.PopularShows

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.influensapp.Data.Entities.Show.Show
import com.example.influensapp.Data.Repo.Network
import com.example.influensapp.Data.RestApi.SERVER_IMAGES_URL
import com.example.influensapp.R
import com.example.influensapp.Views.ShowSingle.ShowSingleActivity
import kotlinx.android.synthetic.main.popular_show_item.view.*
import kotlinx.android.synthetic.main.popular_show_item_loading.view.*

class PopularShowPagedAdapter(public val context: Context) : PagedListAdapter<Show,RecyclerView.ViewHolder> (ShowDiffCallBack()){
    val SHOW_SHOW = 1
    val SHOW_NETWORK = 2

    private var networkStatus : Network? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val layoutInflater :  LayoutInflater = LayoutInflater.from(parent.context)
            val view : View
        if(viewType == SHOW_SHOW){
            view = layoutInflater.inflate(R.layout.popular_show_item , parent,false)
            return PopularShowItemViewHolder(view)
        }
        else{
            view = layoutInflater.inflate(R.layout.popular_show_item_loading , parent,false)
            return PopularShowItemViewHolder.NetworkStatusViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(getItemViewType(position) == SHOW_SHOW){
                (holder as PopularShowItemViewHolder).bind(getItem(position),context)
            }else{
                (holder as PopularShowItemViewHolder.NetworkStatusViewHolder).bind(networkStatus)
            }

    }
    private fun thereIsMore():Boolean{
        return networkStatus != null && networkStatus == Network.SUCCESS
    }

    override fun getItemViewType(position: Int): Int {
        return if(thereIsMore() && position == itemCount-1)
            SHOW_NETWORK
        else
            SHOW_SHOW
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if(thereIsMore()) 1 else 0
    }

    class ShowDiffCallBack : DiffUtil.ItemCallback<Show>(){
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return  oldItem == newItem
        }

    }

    class PopularShowItemViewHolder (view : View) : RecyclerView.ViewHolder(view){
        fun bind(show: Show?, context: Context){
            itemView.tv_title_popular_show_item.text = show?.name
            val imgUrl = SERVER_IMAGES_URL + show?.posterPath
            Glide.with(context)
                .load(imgUrl)
                .into(itemView.iv_popular_show_item)
            itemView.setOnClickListener {
                val intent = Intent(context,ShowSingleActivity::class.java)
                intent.putExtra("id",show?.id)
                context.startActivity(intent)
            }
        }

        class NetworkStatusViewHolder (view: View) :  RecyclerView.ViewHolder(view){
            fun bind(networkStatus: Network?){
                if (networkStatus != null && networkStatus == Network.LOADING){
                    itemView.progressbar_popular_show_item_loading.visibility = View.VISIBLE
                }
                else{
                    itemView.progressbar_popular_show_item_loading.visibility = View.GONE

                }
                if (networkStatus != null && networkStatus == Network.ERROR){
                    itemView.tv_error_popular_show_item_loading.visibility = View.VISIBLE
                    itemView.tv_error_popular_show_item_loading.text = networkStatus.message
                }
                else{
                    if(networkStatus == Network.NO_MORE_SHOWS){
                        itemView.tv_error_popular_show_item_loading.visibility = View.VISIBLE
                        itemView.tv_error_popular_show_item_loading.text = networkStatus.message
                    }
                    else
                    itemView.tv_error_popular_show_item_loading.visibility = View.GONE

                }
            }
        }
    }

    fun setNetworkStatus(networkStatus: Network){
        val oldNetworkStatus =  this.networkStatus
        val thereIsMore : Boolean = thereIsMore()
        this.networkStatus = networkStatus
        val haveMore : Boolean = thereIsMore()
        if(thereIsMore!=haveMore){
            if(thereIsMore){
                notifyItemRemoved(super.getItemCount())
            }
            else{
                notifyItemInserted(super.getItemCount())
            }
        }else if (haveMore && oldNetworkStatus != networkStatus){
            notifyItemChanged(itemCount-1)
        }
    }

}