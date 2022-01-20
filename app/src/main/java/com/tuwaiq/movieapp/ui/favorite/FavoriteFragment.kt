package com.tuwaiq.movieapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.data.local.FavoriteMovie
import com.tuwaiq.movieapp.data.model.Movie
import com.tuwaiq.movieapp.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)
        val adapter = FavoriteAdapter()

        viewModel.movies.observe(viewLifecycleOwner,{
            adapter.setMovieList(it.distinct())
            binding.apply {
                emptyFavList.isVisible = false
                emptyTextList.isVisible = false
                rvMovie.setHasFixedSize(true)
                rvMovie.adapter = adapter
                if (it.isNullOrEmpty()) {
                    emptyFavList.isVisible = true
                    emptyTextList.isVisible = true
                }else{
                    emptyFavList.isVisible = false
                    emptyTextList.isVisible = false
                }
            }
        })
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClick(favoriteMovie: FavoriteMovie) {
                val movie = Movie(
                    favoriteMovie.id_movie,
                    favoriteMovie.overview,
                    favoriteMovie.poster_path,
                    favoriteMovie.original_title,
                    favoriteMovie.vote_average,
                    favoriteMovie.release_date,
                    favoriteMovie.original_language,
                    favoriteMovie.popularity,
                    favoriteMovie.vote_count,
                    favoriteMovie.adult
                )
                val action = FavoriteFragmentDirections.actionNavFavoriteToNavDetails(movie)
                findNavController().navigate(action)
            }

        })
    }
}