package io.vasilenko.otus.moviesearcher.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int? = 1): Call<TopMoviesResultDto>
}