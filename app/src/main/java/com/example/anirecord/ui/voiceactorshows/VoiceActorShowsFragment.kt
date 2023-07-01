package com.example.anirecord.ui.voiceactorshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentVoiceActorShowListBinding
import com.example.anirecord.domain.model.VoiceActorShowsListItemModel
import com.example.anirecord.ui.utils.InfiniteScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoiceActorShowsFragment : Fragment(), VoiceActorShowsAdapter.VoiceActorShowClickHandler {
    private var _bindings: FragmentVoiceActorShowListBinding? = null
    private val vm: VoiceActorShowsViewModel by viewModels()

    private val bindings get() = _bindings!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindings = FragmentVoiceActorShowListBinding.inflate(layoutInflater, container, false)

        val listAdapter = VoiceActorShowsAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        bindings.voiceActorShowList.apply {
            adapter = listAdapter
            layoutManager = recyclerLayoutManager
            addOnScrollListener(InfiniteScrollListener(vm::load, recyclerLayoutManager))
        }

        vm.shows.observe(viewLifecycleOwner, listAdapter::update)

        return bindings.root
    }

    override fun onShowClick(show: VoiceActorShowsListItemModel) {
        findNavController().navigate(VoiceActorShowsFragmentDirections.navToDetail(show.mediaId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindings = null
    }
}