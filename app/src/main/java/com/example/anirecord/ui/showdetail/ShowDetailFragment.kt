package com.example.anirecord.ui.showdetail

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.anirecord.R
import com.example.anirecord.databinding.FragmentShowDetailBinding
import com.example.anirecord.type.MediaStatus
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects
import kotlin.properties.Delegates

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val vm: ShowDetailViewModel by viewModels()
    private var shortAnimationDuration by Delegates.notNull<Int>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)

        binding.showDetailContainer.visibility = View.GONE
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        vm.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ShowDetailViewModel.UiState.Success -> {
                    val show = uiState.show
                    Picasso.get()
                        .load(show.cover)
                        .placeholder(R.drawable.img_placeholder)
                        .into(binding.showDetailCover)
                    binding.showDetailTitle.text = show.title
                    binding.showDetailDateSeason.text = show.season?.name ?: ""
                    binding.showDetailDateYear.text = show.year?.toString() ?: ""
                    val html = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(show.description, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(show.description)
                    }
                    val (statusResource, statusLabel) = when (show.status) {
                        MediaStatus.RELEASING -> Pair(
                            R.drawable.play,
                            R.string.show_status_releasing
                        )

                        MediaStatus.FINISHED -> Pair(
                            R.drawable.check,
                            R.string.show_status_finished
                        )

                        MediaStatus.CANCELLED -> Pair(
                            R.drawable.close,
                            R.string.show_status_cancelled
                        )

                        MediaStatus.HIATUS -> Pair(R.drawable.pause, R.string.show_status_hiatus)
                        MediaStatus.NOT_YET_RELEASED -> Pair(
                            R.drawable.schedule,
                            R.string.show_status_to_be_released
                        )

                        else -> Pair(R.drawable.question_mark, R.string.show_status_unknown)
                    }

                    if (Objects.nonNull(show.averageScore)) {
                        binding.showDetailRatingInfo.visibility = View.VISIBLE
                        binding.showDetailRatingValue.text = show.ratingString
                    }

                    binding.showDetailStatusIcon.setImageResource(statusResource)
                    binding.showDetailStatusLabel.text = getString(statusLabel)
                    binding.showDetailDescription.text = html

                    crossfade()
                }

                else -> {}
            }
        }

        return binding.root
    }

    private fun crossfade() {
        binding.showDetailContainer.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        binding.showDetailLoadingSpinner.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.showDetailLoadingSpinner.visibility = View.GONE
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}