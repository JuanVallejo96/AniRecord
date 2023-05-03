package com.example.anirecord.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentHomeBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.ui.utils.InfiniteScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SeriesListAdapter.SeriesClickHandler {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm: HomeViewModel by viewModel()

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

        vm.shows.observe(viewLifecycleOwner, listAdapter::addItems)

        return binding.root
    }

    override fun onClick(show: ShowListItemModel) {
        findNavController().navigate(HomeFragmentDirections.navToDetail(show.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}