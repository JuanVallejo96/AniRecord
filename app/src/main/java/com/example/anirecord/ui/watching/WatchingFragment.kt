package com.example.anirecord.ui.watching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentWatchingBinding
import com.example.anirecord.domain.model.ShowProgressListItemModel
import com.example.anirecord.ui.adapters.SeriesWithProgressListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchingFragment : Fragment(), SeriesWithProgressListAdapter.SeriesClickHandler {
    private var _binding: FragmentWatchingBinding? = null
    private val vm: WatchingViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchingBinding.inflate(inflater, container, false)

        val listAdapter = SeriesWithProgressListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.watchingShows.apply {
            adapter = listAdapter
            layoutManager = recyclerLayoutManager
        }

        vm.shows.observe(viewLifecycleOwner, listAdapter::replaceAll)

        return binding.root
    }

    override fun onShowClick(show: ShowProgressListItemModel) {
        findNavController().navigate(WatchingFragmentDirections.navToDetail(show.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}