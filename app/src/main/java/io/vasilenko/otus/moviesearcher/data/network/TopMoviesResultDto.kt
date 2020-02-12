package io.vasilenko.otus.moviesearcher.data.network

import com.google.gson.annotations.SerializedName

data class TopMoviesResultDto(

    @SerializedName("page")
    var page: Int = 0,

    @SerializedName("results")
    var movies: List<MovieDto>
)