package io.vasilenko.otus.moviesearcher.data.local

object LocalMovieStorage {

    val movies = listOf(
        LocalMovieDto("Alien", "Some description", "8.5/10", "movie_alien"),
        LocalMovieDto("Godfather", "Some description", "8.5/10", "movie_godfather"),
        LocalMovieDto("Forest Gump", "Some description", "8.5/10", "movie_forest_gump"),
        LocalMovieDto("Fight Club", "Some description", "8.5/10", "movie_fight_club"),
        LocalMovieDto("Black Knight", "Some description", "8.5/10", "movie_black_knight")
    )
}