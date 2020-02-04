package io.vasilenko.otus.moviesearcher.data.mapper

import io.vasilenko.otus.moviesearcher.data.local.LocalMovieDto
import io.vasilenko.otus.moviesearcher.domain.entity.MovieEntity

class LocalToDomainMovieMapper {

    fun mapLocalMovieDtoToMovieEntity(moviesDtoLocal: List<LocalMovieDto>): List<MovieEntity> {
        return moviesDtoLocal.map {
            MovieEntity(
                null,
                it.title,
                it.description,
                it.rating,
                it.imgName
            )
        }
    }

    fun mapMovieEntityToLocalMovie(movie: MovieEntity): LocalMovieDto {
        return LocalMovieDto(movie.title, movie.description, movie.rating, movie.imgName)
    }
}