package io.vasilenko.otus.moviesearcher.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import io.vasilenko.otus.moviesearcher.presentation.view.MovieDetailsView
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment(), MovieDetailsView {

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsBackButton.setOnClickListener { requireActivity().onBackPressed() }
        viewModel = ViewModelProvider(requireActivity()).get(MovieDetailsViewModel::class.java)
        viewModel.movie.observe(viewLifecycleOwner, Observer { showMovie(it) })
    }

    override fun showMovie(movie: MovieModel) {
        movieDescription.text = movie.description
        movieRating.text = movie.rating
        Glide.with(this)
            .load(movie.backdropPath)
            .placeholder(R.drawable.ic_movie_default)
            .fallback(R.drawable.ic_movie_default)
            .error(R.drawable.ic_movie_default)
            .into(movieImg)
    }
}