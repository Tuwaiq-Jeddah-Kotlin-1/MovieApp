package com.tuwaiq.movieapp.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<DetailsMovieModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val movie = args.movie
            Glide.with(this@DetailsFragment)
                .load("${movie.baseUrl}${movie.poster_path}")
                .error(R.drawable.logo)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progressBar.isVisible = false
                        ivShareMovie.isVisible = true
                        toggleFavorite.isVisible = true
                        tvDescription.isVisible = true
                        tvMovieTitle.isVisible = true
                        tvMovieRate.isVisible = true
                        tvMovieTime.isVisible = true
                        tvMovieLanguage.isVisible = true
                        tvMoviePopularity.isVisible = true
                        tvPersonsRate.isVisible = true
                        tvMovieAdult.isVisible = true
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progressBar.isVisible = false
                        ivShareMovie.isVisible = true
                        toggleFavorite.isVisible = true
                        tvDescription.isVisible = true
                        tvMovieTitle.isVisible = true
                        tvMovieRate.isVisible = true
                        tvMovieTime.isVisible = true
                        tvMovieLanguage.isVisible = true
                        tvMoviePopularity.isVisible = true
                        tvPersonsRate.isVisible = true
                        tvMovieAdult.isVisible = true
                        return false
                    }

                })
                .into(ivMoviePoster)
            tvDescription.text = movie.overview
            tvMovieTitle.text = movie.original_title
            tvMovieRate.text = movie.vote_average.toString()
            tvMovieTime.text = movie.release_date
            tvMovieLanguage.text = movie.original_language
            tvMoviePopularity.text = movie.popularity.toString()
            tvPersonsRate.text = movie.vote_count.toString()
            tvMovieAdult.text = movie.adult.toString()

            var _isChecked = false
            viewModel.checkMovie(movie.id)
            viewModel.liveData.observe(viewLifecycleOwner) {
                toggleFavorite.isChecked = it
                _isChecked = it
            }

            toggleFavorite.setOnClickListener {
                _isChecked = !_isChecked
                if (_isChecked) {
                    viewModel.addToFavorite(movie)
                } else {
                    viewModel.removeFromFavorite(movie.id)
                }
                toggleFavorite.isChecked = _isChecked
            }

            binding.ivShareMovie.setOnClickListener {
                val shareMovie = Intent(Intent.ACTION_SEND)
                shareMovie.type = "text/plain"
                shareMovie.putExtra(Intent.EXTRA_TEXT,
                    "I recommend you to watch \n Movie: ${movie.original_title} \n Rating:  ${movie.vote_average}")
                startActivity(shareMovie)
            }
        }
    }
}
