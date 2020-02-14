package com.example.influensapp.Data.Entities.PopularShows


import com.example.influensapp.Data.Entities.Show.Show
import com.google.gson.annotations.SerializedName

data class PopularShows(
    val page: Int,
    val results: List<Show>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)