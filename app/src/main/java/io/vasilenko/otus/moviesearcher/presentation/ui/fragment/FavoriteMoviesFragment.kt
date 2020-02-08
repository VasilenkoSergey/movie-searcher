package io.vasilenko.otus.moviesearcher.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.vasilenko.otus.moviesearcher.MovieSearcherApp
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouter
import io.vasilenko.otus.moviesearcher.presentation.presenter.FavoriteMoviesPresenter
import io.vasilenko.otus.moviesearcher.presentation.ui.activity.MovieDetailActivity
import io.vasilenko.otus.moviesearcher.presentation.ui.adapter.FavoriteMoviesAdapter
import io.vasilenko.otus.moviesearcher.presentation.ui.decoration.MovieItemDecoration
import io.vasilenko.otus.moviesearcher.presentation.view.FavoriteMoviesView
import kotlinx.android.synthetic.main.activity_favorite_movies.*
import kotlinx.android.synthetic.main.activity_movies.*

class FavoriteMoviesFragment : Fragment(), FavoriteMoviesView {

    lateinit var router: MoviesRouter
    lateinit var presenter: FavoriteMoviesPresenter
    private lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = MovieSearcherApp.router
        presenter = MovieSearcherApp.favoriteMoviesPresenter
        presenter.attachView(this@FavoriteMoviesFragment)
        setupViews()
        getFavoriteMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun getFavoriteMovies() {
        presenter.loadFavoriteMovies()
    }

    override fun showFavoriteMovies(movies: List<MovieModel>) {
        if (movies.isEmpty()) showEmptyPlaceHolder() else favoriteMoviesAdapter.setMovies(movies)
    }

    private fun setupViews() {
        activity?.moviesAppToolBarText?.text = getString(R.string.movies_favorite_toolbar_title)
        favoriteMoviesAdapter = FavoriteMoviesAdapter { movie -> movieClickListener(movie) }
        favoriteMoviesRv.layoutManager = LinearLayoutManager(requireContext())
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

    private fun movieClickListener(movie: MovieModel) {
        router.onOpenActivity(Intent(requireContext(), MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        })
    }

    private fun showEmptyPlaceHolder() {
        favoriteMoviesEmptyPlaceholder.visibility = View.VISIBLE
    }
}