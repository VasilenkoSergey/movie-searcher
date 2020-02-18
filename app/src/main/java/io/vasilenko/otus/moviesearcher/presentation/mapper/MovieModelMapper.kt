package io.vasilenko.otus.moviesearcher.presentation.mapper

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

class MovieModelMapper {

    fun mapMovieEntitiesToModels(movies: List<MovieEntity>): List<MovieModel> {
        return movies.map {
            MovieModel(
                title = it.title,
                description = it.description,
                rating = it.rating,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath
            )
        }
    }

    fun mapMovieModelToEntity(movie: MovieModel): MovieEntity {
        return MovieEntity(
            id = null,
            title = movie.title,
            description = movie.description,
            rating = movie.rating,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath
        )
    }
}