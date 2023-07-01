package com.example.anirecord.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentHomeBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.ui.adapters.SeriesListAdapter
import com.example.anirecord.ui.utils.InfiniteScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), SeriesListAdapter.SeriesClickHandler {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val listAdapter = SeriesListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.seriesList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
            addOnScrollListener(InfiniteScrollListener(vm::load, recyclerLayoutManager))
        }

        binding.searchFab.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.navToSearch())
        }

        vm.shows.observe(viewLifecycleOwner, listAdapter::update)

        return binding.root
    }

    override fun onShowClick(show: ShowListItemModel) {
        findNavController().navigate(HomeFragmentDirections.navToDetail(show.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}