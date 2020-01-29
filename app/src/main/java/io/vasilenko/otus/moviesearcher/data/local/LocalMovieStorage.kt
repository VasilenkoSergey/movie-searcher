package io.vasilenko.otus.moviesearcher.data.local

object LocalMovieStorage {

    private const val description = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam efficitur ipsum in placerat molestie. 
        Fusce quis mauris a enim sollicitudin ultrices non eget velit. Aliquam eu pulvinar enim. Praesent 
        libero nisl, ultricies vel maximus eu, maximus et nisi. Phasellus pharetra nec ligula vitae mollis. 
        Quisque porttitor ornare magna, quis dignissim libero aliquam id. Donec sollicitudin ultrices ante, 
        a semper erat tristique in. Vivamus sodales in ligula at sodales. Pellentesque habitant morbi tristique 
        senectus et netus et malesuada fames ac turpis egestas. Nulla tempus bibendum nibh, ac tincidunt quam convallis non.
    """

    val movies = listOf(
        LocalMovieDto("Alien", description, "8.5/10", "movie_alien"),
        LocalMovieDto("Godfather", description, "8.5/10", "movie_godfather"),
        LocalMovieDto("Forest Gump", description, "8.5/10", "movie_forest_gump"),
        LocalMovieDto("Fight Club", description, "8.5/10", "movie_fight_club"),
        LocalMovieDto("Black Knight", description, "8.5/10", "movie_black_knight")
    )
}