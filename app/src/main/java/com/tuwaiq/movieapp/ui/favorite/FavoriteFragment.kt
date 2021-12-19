package com.tuwaiq.movieapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.ui.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel by viewModels<MovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movies.observe(viewLifecycleOwner) {

        }
    }

}