package com.example.moviesearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.data.model.SearchResult
import com.example.moviesearch.databinding.ItemMovieBinding

class SearchResultsAdapter(
    private val itemClick: OnMovieClickedListener
) : RecyclerView.Adapter<SearchResultsAdapter.MovieViewHolder>() {

    private var movies: List<SearchResult.Search> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            itemClick.onMovieClicked(movie)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun submitList(movies: List<SearchResult.Search>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: SearchResult.Search) {
            binding.moviePlot.text = movie.type
            binding.movieTitle.text = movie.title
            binding.movieYear.text = movie.year.toString()
            Glide.with(binding.root.context)
                .load(movie.poster)
                .placeholder(R.drawable.ic_movie_placeholder)
                .into(binding.moviePoster)
        }
    }

    interface OnMovieClickedListener {
        fun onMovieClicked(movie: SearchResult.Search)
    }
}
