package com.example.anirecord.ui.showdetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.R
import com.example.anirecord.databinding.FragmentShowDetailBinding
import com.example.anirecord.databinding.ShowDetailBottomSheetBinding
import com.example.anirecord.domain.model.CharacterConnectionModel
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.domain.model.ShowDetailModel
import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.graphql.type.MediaStatus
import com.example.anirecord.ui.adapters.ShowDetailBottomSheetListsAdapter
import com.example.anirecord.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import kotlin.properties.Delegates


@AndroidEntryPoint
class ShowDetailFragment : Fragment(), CharacterConnectionListAdapter.CharacterClickHandler,
    StaffListAdapter.StaffClickHandler,
    ShowDetailBottomSheetListsAdapter.ShowDetailBottomSheetListsClickHandler {
    private var _binding: FragmentShowDetailBinding? = null
    private val vm: ShowDetailViewModel by viewModels()
    private var shortAnimationDuration by Delegates.notNull<Int>()
    private var favMenu: MenuItem? = null
    private var pendingMenu: MenuItem? = null
    private lateinit var lastChapterAdapter: ArrayAdapter<String>

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

        lastChapterAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.show_chapter_dropdown_item,
            R.id.chapterDropdownItem,
        )

        vm.show.observe(viewLifecycleOwner) { show ->
            favMenu?.isVisible = true
            pendingMenu?.isVisible = true
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
        val html = Utils.htmlToSpanned(show.description)
        binding.showDetailDescription.text = html

        binding.showDetailDateSeason.text = show.season?.name ?: ""
        binding.showDetailDateYear.text = show.year?.toString() ?: ""

        setStatus(show)
        setFavourite(show)
        setPending(show)
        setAverageScore(show)
        setNextEpisode(show)
        setCharactersAndActors(show)
        setStaff(show)
        setLastChapterAndProgress(show)
    }

    private fun setLastChapterAndProgress(show: ShowDetailModel) {
        val chapterList = generateChapterList(show.nextEpisode?.episode, show.episodes)
        binding.lastChapterDropdown.apply {
            setAdapter(lastChapterAdapter)
            onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val episode = chapterList[position].first.let {
                        if (it == 0) {
                            null
                        } else {
                            it
                        }
                    }
                    vm.updateProgress(episode)
                }
            }
        }
        lastChapterAdapter.clear()
        lastChapterAdapter.addAll(chapterList.map { it.second })
        binding.lastChapterDropdown.setText(chapterList[show.progress ?: 0].second, false)

        val showLastSeenChapter = let {
            val nextEpisodeNumber = show.nextEpisode?.episode
            when {
                nextEpisodeNumber == null && show.episodes == null -> false
                nextEpisodeNumber == 1 -> false
                (show.episodes != null) && (nextEpisodeNumber != null) && (nextEpisodeNumber > show.episodes) -> false
                else -> true
            }
        }

        binding.lastChapterContainer.isVisible = showLastSeenChapter

        if (showLastSeenChapter && show.progress != null) {
            binding.showDetailProgressLabel.isVisible = true
            if (show.episodes != null && show.progress != 0) {
                binding.showDetailProgressBar.isVisible = true
                val percent = (100.0 * show.progress!! / show.episodes).toInt()
                binding.showDetailProgressBar.progress = percent
                binding.showDetailProgressLabel.text = requireContext().getString(
                    R.string.watched_progress_label,
                    show.progress,
                    show.episodes
                )
            } else {
                binding.showDetailProgressBar.isVisible = false
                binding.showDetailProgressLabel.text = requireContext().getString(
                    R.string.show_still_airing_label,
                    show.progress,
                )
            }
        } else {
            binding.showDetailProgressBar.isVisible = false
            binding.showDetailProgressLabel.isVisible = false
        }
    }

    private fun generateChapterList(
        nextEpisode: Int?,
        episodes: Int?
    ): List<Pair<Int, String>> {
        val selectableEpisodes =
            Integer.min(nextEpisode ?: Int.MAX_VALUE, episodes ?: Int.MAX_VALUE)
        if (Int.MAX_VALUE == selectableEpisodes) return listOf()
        return (0..selectableEpisodes).map {
            val label = if (it == 0) {
                requireContext().getString(R.string.not_watched_label)
            } else {
                requireContext().getString(R.string.episode_n_label, it)
            }
            Pair(it, label)
        }
    }

    private fun setStatus(show: ShowDetailModel) {
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
        binding.showDetailStatusLabel.text = getString(statusLabel)
        binding.showDetailStatusIcon.setImageResource(statusResource)
    }

    private fun setAverageScore(show: ShowDetailModel) {
        if (Objects.nonNull(show.averageScore)) {
            binding.showDetailRatingInfo.visibility = View.VISIBLE
            binding.showDetailRatingValue.text = show.ratingString
        }
    }

    private fun setPending(show: ShowDetailModel) {
        val bookmarkIcon = if (show.isPending) {
            R.drawable.bookmark_filled_white
        } else {
            R.drawable.bookmark_white
        }
        pendingMenu?.setIcon(bookmarkIcon)
    }

    private fun setFavourite(show: ShowDetailModel) {
        val starIcon = if (show.isFavourite) {
            R.drawable.star_filled_white
        } else {
            R.drawable.star_white
        }
        favMenu?.setIcon(starIcon)
    }

    private fun setNextEpisode(show: ShowDetailModel) {
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
    }

    private fun setCharactersAndActors(show: ShowDetailModel) {
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
    }

    private fun setStaff(show: ShowDetailModel) {
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
                pendingMenu = menu.findItem(R.id.showDetailMenuPending)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.showDetailMenuFavourite -> {
                        vm.toggleFavourite()
                        true
                    }

                    R.id.showDetailMenuPending -> {
                        vm.togglePending()
                        true
                    }

                    R.id.add_to_list -> {
                        if (vm.hasLists()) {
                            showLists()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                R.string.has_not_list,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    private fun showLists(): Boolean {
        val dialog = BottomSheetDialog(requireContext())
        val dialogLayoutInflater = dialog.layoutInflater
        val dialogBindings = ShowDetailBottomSheetBinding.inflate(
            dialogLayoutInflater,
            binding.root,
            false,
        )

        dialog.setContentView(dialogBindings.root)
        val listAdapter = ShowDetailBottomSheetListsAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        dialogBindings.showDetailListSelectorRecycler.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
        }

        vm.lists.observe(dialog) {
            listAdapter.replaceAll(it)
        }

        dialog.setCancelable(true)
        dialog.show()
        return true
    }

    override fun onListSelectorClick(list: ListCollectionItemModel) {
        vm.toggleList(list.id)
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

    override fun onCharacterClick(characterConnection: CharacterConnectionModel) {
        characterConnection.actorId?.let { actorId ->
            findNavController().navigate(
                ShowDetailFragmentDirections.navToVoiceActorShowsList(actorId)
            )
        }
    }

    override fun onStaffClickHandler(staff: ShowStaffListItemModel) {
        findNavController().navigate(
            ShowDetailFragmentDirections.navToStaffShowsList(staff.id)
        )
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