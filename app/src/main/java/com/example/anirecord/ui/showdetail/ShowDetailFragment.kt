package com.example.anirecord.ui.showdetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.anirecord.databinding.FragmentShowDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val vm: ShowDetailViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)

        vm.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ShowDetailViewModel.UiState.Loading -> {
                    //TODO("Spinner (?)")
                }

                is ShowDetailViewModel.UiState.Success -> {
                    val show = uiState.show
                    Picasso.get().load(show.cover).into(binding.showDetailCover)
                    binding.showDetailTitle.text = show.title
                    binding.showDetailDateSeason.text = show.season?.name ?: ""
                    binding.showDetailDateYear.text = show.year?.toString() ?: ""
                    binding.showDetailRatingValue.text = show.ratingString
                    val html = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(show.description, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(show.description)
                    }
                    binding.showDetailDescription.text = html
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}