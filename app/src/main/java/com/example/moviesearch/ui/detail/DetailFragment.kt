package com.example.moviesearch.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.data.repository.MovieRepository
import com.example.moviesearch.databinding.FragmentDetailBinding
import com.example.moviesearch.util.NetworkState

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)


        viewModel = ViewModelProvider(
            this,
            DetailViewModelFactory(MovieRepository())
        )[DetailViewModel::class.java]

        args.movieId?.let { viewModel.getMovieDetail(it) }


        viewModel.movie.observe(viewLifecycleOwner) {
            // Set movie details to views
            binding.movieTitle.text = it.title
            binding.movieYear.text = it.year.toString()
            binding.moviePlot.text = it.plot
            binding.movieGenre.text = it.genre
            binding.movieDirector.text = it.director
            binding.movieActors.text = it.actors
            binding.movieAwards.text = it.awards

            // Load movie poster using Glide library
            Glide.with(this)
                .load(it.poster)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_error)
                .into(binding.moviePoster)

        }

        viewModel.networkState.observe(viewLifecycleOwner) { state ->
            when (state) {
                NetworkState.LOADING -> {

                }
                NetworkState.LOADED -> {

                }
                NetworkState.error(state.message) -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        return binding.root
    }

}

