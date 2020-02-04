package io.vasilenko.otus.moviesearcher.presentation.mapper

import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel

class MovieModelMapper {

    fun mapMovieEntitiesToModels(movies: List<MovieEntity>): List<MovieModel> {
        return movies.map {
            MovieModel(
                it.title,
                it.description,
                it.rating,
                it.imgName
            )
        }
    }

    fun mapMovieModelToEntity(movie: MovieModel): MovieEntity {
        return MovieEntity(null, movie.title, movie.description, movie.rating, movie.imageName)
    }
}