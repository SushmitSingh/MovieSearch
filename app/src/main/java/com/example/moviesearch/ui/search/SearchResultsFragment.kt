package com.example.moviesearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.data.model.SearchResult
import com.example.moviesearch.data.repository.MovieRepository
import com.example.moviesearch.databinding.FragmentSearchResultsBinding
import com.example.moviesearch.util.NetworkState

class SearchResultsFragment : Fragment(), SearchResultsAdapter.OnMovieClickedListener {
    private lateinit var viewModel: SearchResultsViewModel
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private lateinit var binding: FragmentSearchResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        val recyclerView = binding.recyclerView
        searchResultsAdapter = SearchResultsAdapter(this)
        recyclerView.adapter = searchResultsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(
            this, SearchResultsViewModelFactory(MovieRepository())
        )[SearchResultsViewModel::class.java]

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.nextButton.setOnClickListener {
            viewModel.loadNextPage()
        }

        binding.preButton.setOnClickListener {
            viewModel.loadPreviousPage()
        }

        viewModel.networkState.observe(viewLifecycleOwner) { state ->
            when (state) {
                NetworkState.LOADING -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                NetworkState.LOADED -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                NetworkState.error(state.message) -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.searchResults.observe(viewLifecycleOwner) {
            searchResultsAdapter.submitList(it)
            if (it.size < 10) {
                binding.nextButton.visibility = View.GONE
            } else {
                binding.nextButton.visibility = View.VISIBLE
            }
        }

        viewModel.getCurrentPage().observe(viewLifecycleOwner) { currentPage ->
            binding.pages.text = "Current Page :$currentPage"

            if (currentPage == 1) {
                binding.preButton.visibility = View.GONE
            } else {
                binding.preButton.visibility = View.VISIBLE
            }
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMovies(it, 1)
                    searchResultsAdapter.submitList(emptyList())
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return binding.root
    }

    override fun onMovieClicked(movie: SearchResult.Search) {
        if (movie.imdbID.isBlank()) {
            Toast.makeText(requireContext(), "No movie found", Toast.LENGTH_SHORT).show()
        } else {
            val action =
                SearchResultsFragmentDirections.actionSearchResultsFragmentToMovieDetailFragment(
                    movie.imdbID, movie.title
                )
            findNavController(requireView()).navigate(action)
        }
    }
}
