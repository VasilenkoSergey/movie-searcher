package io.vasilenko.otus.moviesearcher.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.vasilenko.otus.moviesearcher.MovieSearcherApp
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.common.ItemDecoration
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouter
import io.vasilenko.otus.moviesearcher.presentation.ui.details.MovieDetailsFragment
import io.vasilenko.otus.moviesearcher.presentation.view.FavoriteMoviesView
import kotlinx.android.synthetic.main.fragment_favorite_movies.*

class FavoriteMoviesFragment : Fragment(), FavoriteMoviesView {

    lateinit var router: MoviesRouter
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: FavoriteMoviesViewModel
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
        factory = MovieSearcherApp.favoriteMoviesViewModelFactory
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(FavoriteMoviesViewModel::class.java)
        setupViews()

        viewModel.movies.observe(viewLifecycleOwner, Observer { showFavoriteMovies(it.orEmpty()) })
        getFavoriteMovies()
    }

    override fun getFavoriteMovies() {
        viewModel.loadFavoriteMovies()
    }

    override fun showFavoriteMovies(movies: List<MovieModel>) {
        if (movies.isEmpty()) showEmptyPlaceHolder() else favoriteMoviesAdapter.setMovies(movies)
    }

    private fun setupViews() {
        favoriteMoviesToolbar.title = getString(R.string.movies_favorite_toolbar_title)
        favoriteMoviesAdapter =
            FavoriteMoviesAdapter { movie ->
                movieClickListener(movie)
            }
        favoriteMoviesRv.layoutManager = LinearLayoutManager(requireContext())
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        favoriteMoviesRv.addItemDecoration(
            ItemDecoration(
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
                viewModel.deleteFromFavorites(favoriteMoviesAdapter.getMovieAt(viewHolder.adapterPosition))
                favoriteMoviesAdapter.removeMovie(viewHolder.adapterPosition)
                favoriteMoviesAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                if (favoriteMoviesAdapter.itemCount == 0) showEmptyPlaceHolder()
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(favoriteMoviesRv)
    }

    private fun movieClickListener(movie: MovieModel) {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        val movieDetailsFragment = MovieDetailsFragment.newInstance(args = bundle)
        router.onOpenFragment(movieDetailsFragment, addToBackStack = true, showNavBar = false)
    }

    private fun showEmptyPlaceHolder() {
        favoriteMoviesEmptyPlaceholder.visibility = View.VISIBLE
    }
}