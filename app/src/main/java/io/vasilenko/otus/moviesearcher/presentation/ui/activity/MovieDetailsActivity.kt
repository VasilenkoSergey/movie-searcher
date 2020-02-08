package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.view.MovieDetailsView
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        showMovie()
    }

    override fun showMovie() {
        val movie = intent.getParcelableExtra<MovieModel>("movie")
        movieTitle.text = movie?.title
        movieDescription.text = movie?.description
        movieRating.text = movie?.rating
        movie?.imgId?.let { movieImg.setImageResource(it) }
    }
}