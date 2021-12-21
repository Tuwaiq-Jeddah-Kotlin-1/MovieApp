package com.tuwaiq.movieapp.ui.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.data.remot.Movie
import com.tuwaiq.movieapp.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {
    private val viewModel by viewModels<MovieViewModel>()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieBinding.bind(view)
        val adapter = MovieAdapter()
        binding.apply {
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { adapter.retry() },
                footer = MovieLoadStateAdapter { adapter.retry() }

            )
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

}