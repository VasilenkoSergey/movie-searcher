package io.vasilenko.otus.moviesearcher.presentation.ui.top

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.vasilenko.otus.moviesearcher.MovieSearcherApp
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.core.ext.showSnack
import io.vasilenko.otus.moviesearcher.presentation.common.ItemDecoration
import io.vasilenko.otus.moviesearcher.presentation.common.MessageBundle
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.navigation.MoviesRouter
import io.vasilenko.otus.moviesearcher.presentation.ui.details.MovieDetailsFragment
import io.vasilenko.otus.moviesearcher.presentation.view.TopMoviesView
import kotlinx.android.synthetic.main.fragment_top_movies.*

class TopMoviesFragment : Fragment(), TopMoviesView {

    lateinit var router: MoviesRouter
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: TopMoviesViewModel

    private var isLoading = false

    private lateinit var progressBar: ProgressBar
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
        factory = MovieSearcherApp.topMoviesViewModelFactory
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(TopMoviesViewModel::class.java)
        setupViews()
        viewModel.onViewCreated()
        viewModel.viewState.observe(viewLifecycleOwner, Observer { handleViewState(it) })
        viewModel.messageState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Message.AddToFavorite -> showMessageOnSuccessfulAddingToFavorites(it.movie)
                is Message.FavoriteExist -> showMessageIfMovieExistInFavorites()
                is Message.LoadError -> showErrorMessage(it)
                is Message.LoadNextError -> showErrorMessage(it)
                is Message.RefreshError -> showErrorMessage(it)
            }
        })
    }

    private fun handleViewState(state: TopMoviesViewState) {
        val currentPage = state.currentPage
        if (currentPage == 1) {
            state.movies?.let { topMoviesAdapter.setMovies(it) }
        } else {
            state.movies?.let { topMoviesAdapter.addMovies(it) }
        }
        showLoading(state.isLoading)
    }

    override fun showLoading(state: Boolean) {
        isLoading = state
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showTopMovies(movies: List<MovieModel>) {
        topMoviesAdapter.setMovies(movies)
    }

    override fun updateTopMovies(movies: List<MovieModel>) {
        topMoviesAdapter.addMovies(movies)
    }

    override fun showMessageOnSuccessfulAddingToFavorites(movie: MovieModel) {
        val text = getString(R.string.added_to_favorites)
        val action =
            MessageBundle.Action(getString(R.string.cancel_add_to_favorites), View.OnClickListener {
                viewModel.deleteFromFavorites(movie)
            })
        activity?.showSnack(MessageBundle(text, action))
    }

    override fun showMessageIfMovieExistInFavorites() {
        val text = getString(R.string.already_favorite)
        activity?.showSnack(MessageBundle(text))
    }

    override fun showErrorMessage(message: Message) {
        val text = getString(R.string.server_error)
        val action = MessageBundle.Action(getString(R.string.retry), View.OnClickListener {
            viewModel.retry(message)
        })
        activity?.showSnack(MessageBundle(text, action))
    }

    override fun scrollToPosition(position: Int) {
        val layoutManager = topMoviesRv.layoutManager as GridLayoutManager
        layoutManager.scrollToPosition(position)
    }

    private fun setupViews() {
        topMoviesToolbar.title = getString(R.string.movies_top_toolbar_title)
        progressBar = topMoviesProgressBar
        topMoviesAdapter =
            TopMoviesAdapter(
                { movie -> movieOpenClickListener(movie) },
                { movie -> movieAddToFavoriteClickListener(movie) }
            )
        topMoviesRv.layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT ->
                GridLayoutManager(
                    requireContext(),
                    PORTRAIT_SPAN_COUNT
                )
            else ->
                GridLayoutManager(
                    requireContext(),
                    LANDSCAPE_SPAN_COUNT
                )
        }
        val padding = resources.getDimensionPixelSize(R.dimen.default_padding)
        topMoviesRv.addItemDecoration(
            ItemDecoration(
                padding
            )
        )
        topMoviesRv.adapter = topMoviesAdapter
        topMoviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!isLoading) {
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (dy > 0 && lastVisibleItemPosition == topMoviesAdapter.itemCount - 1) {
                        viewModel.onScrollTopMovies()
                    }
                }
            }
        })
        topMoviesSwipeRefresh.setOnRefreshListener {
            viewModel.onRefreshTopMovies()
            topMoviesSwipeRefresh.isRefreshing = false
        }
    }

    private fun movieOpenClickListener(movie: MovieModel) {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        val movieDetailsFragment = MovieDetailsFragment.newInstance(args = bundle)
        router.onOpenFragment(movieDetailsFragment, addToBackStack = true, showNavBar = false)
    }

    private fun movieAddToFavoriteClickListener(movie: MovieModel) {
        viewModel.addMovieToFavorites(movie)
    }

    private companion object {
        const val PORTRAIT_SPAN_COUNT = 2
        const val LANDSCAPE_SPAN_COUNT = 3
    }
}