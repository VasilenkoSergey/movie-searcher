package io.vasilenko.otus.moviesearcher.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import kotlinx.android.synthetic.main.item_top_movie.view.*

class TopMoviesAdapter(
    private val clickListener: (MovieModel) -> Unit,
    private val longClickListener: (MovieModel) -> Unit
) : RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {

    private var movies: MutableList<MovieModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_top_movie, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(movies[position], clickListener, longClickListener)
    }

    fun setMovies(movieItems: List<MovieModel>) {
        movies.addAll(movieItems)
        notifyDataSetChanged()
    }

    class ViewHolder(movieItemView: View) : RecyclerView.ViewHolder(movieItemView) {

        fun bindItems(
            movieModel: MovieModel,
            clickListener: (MovieModel) -> Unit,
            longClickListener: (MovieModel) -> Unit
        ) = with(itemView) {

            Glide.with(itemView)
                .load(movieModel.posterPath)
                .placeholder(R.drawable.ic_movie_default)
                .fallback(R.drawable.ic_movie_default)
                .error(R.drawable.ic_movie_default)
                .into(topMovieImg)

            topMovieTitle.text = movieModel.title
            topMovieRating.text = movieModel.rating

            setOnClickListener { clickListener(movieModel) }
            setOnLongClickListener { longClickListener(movieModel); true }
        }
    }
}