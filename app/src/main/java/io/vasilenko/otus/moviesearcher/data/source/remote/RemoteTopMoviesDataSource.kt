package io.vasilenko.otus.moviesearcher.data.source.remote

import io.vasilenko.otus.moviesearcher.data.mapper.MovieDtoMapper
import io.vasilenko.otus.moviesearcher.data.network.RestApi
import io.vasilenko.otus.moviesearcher.data.network.TopMoviesResultDto
import io.vasilenko.otus.moviesearcher.data.source.TopMoviesDataSource
import io.vasilenko.otus.moviesearcher.data.source.local.LocalTopMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.domain.interaction.MovieInteractor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteTopMoviesDataSource(
    private val api: RestApi,
    private val mapper: MovieDtoMapper,
    private val topMoviesLocalDataSource: LocalTopMoviesDataSource
) :
    TopMoviesDataSource {

    override fun getAllMovies(listener: MovieInteractor.TopMoviesSearchListener): List<MovieEntity> {
        val call = api.getTopRatedMovies()

        call.enqueue(object : Callback<TopMoviesResultDto> {

            override fun onFailure(call: Call<TopMoviesResultDto>, t: Throwable) {
                listener.onSearchFailure(t)
            }

            override fun onResponse(
                call: Call<TopMoviesResultDto>,
                response: Response<TopMoviesResultDto>
            ) {
                mapper.mapMovieDtoListToEntityList(response.body()?.movies)?.let {
                    topMoviesLocalDataSource.setMovies(it)
                    listener.onSearchFinished(
                        it
                    )
                }
            }

        })
        return emptyList()
    }

    override fun getMoviesByPage(
        listener: MovieInteractor.NextTopMoviesSearchListener,
        page: Int
    ): List<MovieEntity> {
        val call = api.getTopRatedMovies(page)

        call.enqueue(object : Callback<TopMoviesResultDto> {

            override fun onFailure(call: Call<TopMoviesResultDto>, t: Throwable) {
                listener.onNextSearchFailure(t)
            }

            override fun onResponse(
                call: Call<TopMoviesResultDto>,
                response: Response<TopMoviesResultDto>
            ) {
                mapper.mapMovieDtoListToEntityList(response.body()?.movies)?.let {
                    topMoviesLocalDataSource.addMovies(it)
                    listener.onNextSearchFinished(
                        it
                    )
                }
            }

        })
        return emptyList()
    }
}