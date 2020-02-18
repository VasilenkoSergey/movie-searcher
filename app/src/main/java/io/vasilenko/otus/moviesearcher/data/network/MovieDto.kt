package io.vasilenko.otus.moviesearcher.data.network

import com.google.gson.annotations.SerializedName

data class MovieDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("overview")
    val description: String,

    @SerializedName("vote_average")
    val rating: Double,

    @SerializedName("poster_path")
    var posterPath: String? = null,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null
)