package com.example.anirecord.ui.showdetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.R
import com.example.anirecord.databinding.FragmentShowDetailBinding
import com.example.anirecord.domain.model.CharacterConnectionModel
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.graphql.type.MediaStatus
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import kotlin.properties.Delegates

@AndroidEntryPoint
class ShowDetailFragment : Fragment(), CharacterConnectionListAdapter.CharacterClickHandler,
    StaffListAdapter.StaffClickHandler {
    private var _binding: FragmentShowDetailBinding? = null
    private val vm: ShowDetailViewModel by viewModels()
    private var shortAnimationDuration by Delegates.notNull<Int>()
    private var favMenu: MenuItem? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)

        binding.showDetailContainer.visibility = View.GONE
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        setShowMenu()

        vm.show.observe(viewLifecycleOwner) { show ->
            favMenu?.isVisible = true
            binding.showDetailContainer.visibility = View.VISIBLE
            binding.showDetailLoadingSpinner.visibility = View.GONE

            setShowInfo(show)
        }

        return binding.root
    }

    private fun setShowInfo(show: ShowDetailModel) {
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
            MediaStatus.RELEASING -> Pair(R.drawable.play, R.string.show_status_releasing)
            MediaStatus.FINISHED -> Pair(R.drawable.check, R.string.show_status_finished)
            MediaStatus.CANCELLED -> Pair(R.drawable.close, R.string.show_status_cancelled)
            MediaStatus.HIATUS -> Pair(R.drawable.pause, R.string.show_status_hiatus)
            MediaStatus.NOT_YET_RELEASED -> Pair(
                R.drawable.schedule,
                R.string.show_status_to_be_released
            )

            else -> Pair(R.drawable.question_mark, R.string.show_status_unknown)
        }

        val starIcon = if (show.isFavourite) {
            R.drawable.star_filled_white
        } else {
            R.drawable.star_white
        }
        favMenu?.setIcon(starIcon)

        if (Objects.nonNull(show.averageScore)) {
            binding.showDetailRatingInfo.visibility = View.VISIBLE
            binding.showDetailRatingValue.text = show.ratingString
        }

        binding.showDetailStatusIcon.setImageResource(statusResource)
        binding.showDetailStatusLabel.text = getString(statusLabel)
        binding.showDetailDescription.text = html

        // Next episode
        if (show.nextEpisode != null) {
            if (show.nextEpisode.episode == 1) {
                binding.ShowNextEpisode.text = requireContext().getString(
                    R.string.next_episode_not_released_label,
                    getDateTime(show.nextEpisode.airingAt.toString())
                )
            } else {
                binding.ShowNextEpisode.text = requireContext().getString(
                    R.string.next_episode_released_label,
                    show.nextEpisode.episode,
                    getDateTime(show.nextEpisode.airingAt.toString())
                )
            }
        } else {
            binding.ShowNextEpisode.visibility = View.GONE
        }

        // Characters/Voice Actors
        if (show.characters.isNotEmpty()) {
            binding.showDetailAllCharactersButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    clickViewAllCharacters(show.id)
                }
            }
            binding.showDetailCharactersGroup.visibility = View.VISIBLE
            val listAdapter = CharacterConnectionListAdapter(this, show.characters)
            val recyclerLayoutManager = LinearLayoutManager(context)
            recyclerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.showDetailCharacterList.apply {
                layoutManager = recyclerLayoutManager
                adapter = listAdapter
            }
        }

        // Staff
        if (show.staff.isNotEmpty()) {
            binding.showDetailAllStaffButton.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    clickViewAllStaff(show.id)
                }
            }
            binding.showDetailStaffGroup.visibility = View.VISIBLE
            val listAdapter = StaffListAdapter(this, show.staff)
            val recyclerLayoutManager = LinearLayoutManager(context)
            recyclerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.showDetailStaffList.apply {
                layoutManager = recyclerLayoutManager
                adapter = listAdapter
            }
        }
    }

    private fun setShowMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.show_detail_fragment_menu, menu)
                favMenu = menu.findItem(R.id.showDetailMenuFavourite)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.showDetailMenuFavourite -> {
                        vm.toggleFavourite()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    private fun clickViewAllCharacters(showId: Int) {
        findNavController().navigate(
            ShowDetailFragmentDirections.navToVoiceActorList(showId)
        )
    }

    private fun clickViewAllStaff(showId: Int) {
        findNavController().navigate(
            ShowDetailFragmentDirections.navToStaffList(showId)
        )
    }

    override fun onCharacterClick(show: CharacterConnectionModel) {
        // TODO("Not yet implemented")
    }

    override fun onStaffClickHandler(show: ShowStaffListItemModel) {
        // TODO("Not yet implemented")
    }

    private fun getDateTime(s: String): String? {
        return try {
            val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                resources.configuration.locales.get(0)
            } else {
                resources.configuration.locale
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy", locale)
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}