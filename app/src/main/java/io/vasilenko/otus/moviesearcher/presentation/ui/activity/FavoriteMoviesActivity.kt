package io.vasilenko.otus.moviesearcher.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.vasilenko.otus.moviesearcher.MovieSearcherApp.Companion.favoriteMoviesPresenter
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.presenter.FavoriteMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.ui.adapter.FavoriteMoviesAdapter
import io.vasilenko.otus.moviesearcher.presentation.ui.decoration.MovieItemDecoration
import io.vasilenko.otus.moviesearcher.presentation.view.FavoriteMoviesView
import kotlinx.android.synthetic.main.activity_favorite_movies.*

class FavoriteMoviesActivity : AppCompatActivity(), FavoriteMoviesView {

    lateinit var presenter: FavoriteMoviesPresenter

    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movies)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = favoriteMoviesPresenter
        presenter.attachView(this@FavoriteMoviesActivity)
        setupViews()
        getFavoriteMovies()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupViews() {
        favoriteMoviesAdapter = FavoriteMoviesAdapter { movie -> movieClickListener(movie) }
        favoriteMoviesRv.layoutManager = LinearLayoutManager(this@FavoriteMoviesActivity)
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        favoriteMoviesRv.addItemDecoration(
            MovieItemDecoration(
                padding
            )
        )
        favoriteMoviesRv.adapter = favoriteMoviesAdapter

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                presenter.deleteFromFavorites(favoriteMoviesAdapter.getMovieAt(viewHolder.adapterPosition))
                favoriteMoviesAdapter.removeMovie(viewHolder.adapterPosition)
                favoriteMoviesAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                if (favoriteMoviesAdapter.itemCount == 0) showEmptyPlaceHolder()
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(favoriteMoviesRv)
    }

    override fun getFavoriteMovies() {
        presenter.loadFavoriteMovies()
    }

    override fun showFavoriteMovies(movies: List<MovieModel>) {
        if (movies.isEmpty()) showEmptyPlaceHolder() else favoriteMoviesAdapter.setMovies(movies)
    }

    private fun movieClickListener(movie: MovieModel) {
        startActivity(Intent(this@FavoriteMoviesActivity, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        })
    }

    private fun showEmptyPlaceHolder() {
        favoriteMoviesEmptyPlaceholder.visibility = View.VISIBLE
    }
}