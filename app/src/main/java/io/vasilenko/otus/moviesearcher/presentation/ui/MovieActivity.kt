package io.vasilenko.otus.moviesearcher.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        showMovie()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("comment", movieComment.text.toString())
            putExtra("isLiked", movieLike.isChecked)
        })
        super.onBackPressed()
    }

    private fun showMovie() {
        val movie = intent.getParcelableExtra<MovieModel>("movie")
        movieTitle.text = movie?.title
        movieDescription.text = movie?.description
        movieRating.text = movie?.rating
        movie?.imgId?.let { movieImg.setImageResource(it) }
    }
}