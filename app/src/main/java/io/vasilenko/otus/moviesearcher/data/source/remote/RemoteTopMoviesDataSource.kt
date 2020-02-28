package io.vasilenko.otus.moviesearcher.data.source.remote

import io.reactivex.Observable
import io.vasilenko.otus.moviesearcher.data.mapper.MovieDtoMapper
import io.vasilenko.otus.moviesearcher.data.network.RestApi
import io.vasilenko.otus.moviesearcher.data.source.TopMoviesDataSource
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

class RemoteTopMoviesDataSource(
    private val api: RestApi,
    private val mapper: MovieDtoMapper
) : TopMoviesDataSource {

    override fun getAllMovies(page: Int?): Observable<List<MovieEntity>> {
        return api.getTopRatedMovies(page).map { mapper.mapMovieDtoListToEntityList(it.movies) }
    }
}