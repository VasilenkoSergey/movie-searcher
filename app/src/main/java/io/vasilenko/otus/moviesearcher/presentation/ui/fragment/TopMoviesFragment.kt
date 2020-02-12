package io.vasilenko.otus.moviesearcher.presentation.ui.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.vasilenko.otus.moviesearcher.MovieSearcherApp
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouter
import io.vasilenko.otus.moviesearcher.presentation.presenter.TopMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.ui.activity.MovieDetailsActivity
import io.vasilenko.otus.moviesearcher.presentation.ui.adapter.TopMoviesAdapter
import io.vasilenko.otus.moviesearcher.presentation.ui.decoration.MovieItemDecoration
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.fragment_top_movies.*

class TopMoviesFragment : Fragment(), TopMoviesView {

    lateinit var router: MoviesRouter
    lateinit var presenter: TopMoviesPresenter
    private lateinit var topMoviesAdapter: TopMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = MovieSearcherApp.router
        presenter = MovieSearcherApp.topMoviesPresenter
        presenter.attachView(this@TopMoviesFragment)
        setupViews()
        getTopMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getTopMovies() {
        presenter.loadTopMovies()
    }

    override fun showTopMovies(movies: List<MovieModel>) {
        topMoviesAdapter.setMovies(movies)
    }

    override fun showMessageOnSuccessfulAddingToFavorites(movie: MovieModel) {
        Snackbar.make(
            activity?.moviesContainer as View,
            getString(R.string.added_to_favorites),
            Snackbar.LENGTH_SHORT
        ).setAction(getString(R.string.cancel_add_to_favorites)){
            presenter.deleteFromFavorites(movie)
        }.show()
    }

    override fun showMessageIfMovieExistInFavorites() {
        Snackbar.make(
            activity?.moviesContainer as View,
            getString(R.string.already_favorite),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setupViews() {
        activity?.moviesAppToolBarText?.text = getString(R.string.movies_top_toolbar_title)
        topMoviesAdapter = TopMoviesAdapter(
            { movie -> movieOpenClickListener(movie) },
            { movie -> movieAddToFavoriteClickListener(movie) }
        )
        topMoviesRv.layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT ->
                GridLayoutManager(requireContext(), PORTRAIT_SPAN_COUNT)
            else ->
                GridLayoutManager(requireContext(), LANDSCAPE_SPAN_COUNT)
        }
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        topMoviesRv.addItemDecoration(
            MovieItemDecoration(
                padding
            )
        )
        topMoviesRv.adapter = topMoviesAdapter
    }

    private fun movieOpenClickListener(movie: MovieModel) {
        router.onOpenActivity(Intent(requireContext(), MovieDetailsActivity::class.java).apply {
            putExtra("movie", movie)
        })
    }

    private fun movieAddToFavoriteClickListener(movie: MovieModel) {
        presenter.addMovieToFavorites(movie)
    }

    private companion object {
        const val PORTRAIT_SPAN_COUNT = 2
        const val LANDSCAPE_SPAN_COUNT = 3
    }
}