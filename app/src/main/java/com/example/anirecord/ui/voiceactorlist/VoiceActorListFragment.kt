package com.example.anirecord.ui.voiceactorlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentVoiceActorListBinding
import com.example.anirecord.domain.model.ShowVoiceActorModel
import com.example.anirecord.ui.utils.InfiniteScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoiceActorListFragment : Fragment(), VoiceActorListAdapter.VoiceActorClickHandler {
    private var _binding: FragmentVoiceActorListBinding? = null

    private val vm: VoiceActorListViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVoiceActorListBinding.inflate(inflater, container, false)

        val listAdapter = VoiceActorListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.voiceActorList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
            addOnScrollListener(InfiniteScrollListener(vm::load, recyclerLayoutManager))
        }

        vm.voiceActors.observe(viewLifecycleOwner, listAdapter::update)

        return binding.root
    }

    override fun onVoiceActorClick(voiceActor: ShowVoiceActorModel) {
        findNavController().navigate(
            VoiceActorListFragmentDirections.navToVoiceActorShowsList(voiceActor.actorId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}