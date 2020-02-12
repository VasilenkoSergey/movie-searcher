package io.vasilenko.otus.moviesearcher.data.mapper

import io.vasilenko.otus.moviesearcher.data.local.LocalMovieDto
import io.vasilenko.otus.moviesearcher.data.network.MovieDto
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

class MovieDtoMapper {

    companion object {
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
    }

    fun mapLocalMovieDtoToMovieEntity(moviesDtoLocal: List<LocalMovieDto>): List<MovieEntity> {
        return moviesDtoLocal.map {
            MovieEntity(
                id = null,
                title = it.title,
                description = it.description,
                rating = it.rating,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath
            )
        }
    }

    fun mapMovieEntityToLocalMovie(movie: MovieEntity): LocalMovieDto {
        return LocalMovieDto(
            title = movie.title,
            description = movie.description,
            rating = movie.rating,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath
        )
    }

    fun mapMovieDtoListToEntityList(movies: List<MovieDto>?): List<MovieEntity>? {
        return movies?.map {
            MovieEntity(
                id = null,
                title = it.title,
                description = it.description,
                rating = it.rating.toString(),
                posterPath = it.posterPath?.let { posterPath -> POSTER_BASE_URL + posterPath },
                backdropPath = it.backdropPath?.let { backdropPath -> BACKDROP_BASE_URL + backdropPath }
            )
        }
    }
}