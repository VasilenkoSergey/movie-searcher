package io.vasilenko.otus.moviesearcher.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import kotlinx.android.synthetic.main.item_favorite_movie.view.*

class FavoriteMoviesAdapter(private val clickListener: (MovieModel) -> Unit) :
    RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {

    private var movies: MutableList<MovieModel> = mutableListOf()

    fun setMovies(movieItems: List<MovieModel>) {
        movies.addAll(movieItems)
        notifyDataSetChanged()
    }

    fun removeMovie(position: Int) {
        movies.removeAt(position)
    }

    fun getMovieAt(position: Int): MovieModel {
        return (movies[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_movie, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(movies[position], clickListener)
    }

    class ViewHolder(movieItemView: View) : RecyclerView.ViewHolder(movieItemView) {

        fun bindItems(movieModel: MovieModel, clickListener: (MovieModel) -> Unit) =
            with(itemView) {

                Glide.with(itemView)
                    .load(movieModel.posterPath)
                    .placeholder(R.drawable.ic_movie_default)
                    .fallback(R.drawable.ic_movie_default)
                    .error(R.drawable.ic_movie_default)
                    .into(favoriteMovieImg)

                favoriteMovieTitle.text = movieModel.title
                favoriteMovieRating.text = movieModel.rating

                setOnClickListener { clickListener(movieModel) }
            }
    }
}