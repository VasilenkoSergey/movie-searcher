package io.vasilenko.otus.moviesearcher.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.vasilenko.otus.moviesearcher.R
import io.vasilenko.otus.moviesearcher.presentation.model.MovieModel
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesListAdapter(private val listener: (MovieModel) -> Unit) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    private var movies: MutableList<MovieModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(movies[position], listener)
    }

    fun setMovies(movieItems: List<MovieModel>) {
        movies.addAll(movieItems)
        notifyDataSetChanged()
    }

    class ViewHolder(movieItemView: View) : RecyclerView.ViewHolder(movieItemView) {

        fun bindItems(movieModel: MovieModel, listener: (MovieModel) -> Unit) =
            with(itemView) {
                movieModel.imgId = movieImg.context.resources.getIdentifier(
                    movieModel.imageName, "drawable", movieImg.context.packageName
                )
                movieTitle.text = movieModel.title
                movieRating.text = movieModel.rating
                movieImg.setImageResource(
                    if (movieModel.imgId != EMPTY_RESOURCE_ID) movieModel.imgId else R.drawable.movie_default
                )
                setOnClickListener { listener(movieModel) }
            }
    }

    companion object {
        const val EMPTY_RESOURCE_ID = 0
    }
}