package com.example.anirecord.ui.staffshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentStaffShowListBinding
import com.example.anirecord.domain.model.StaffShowListItemModel
import com.example.anirecord.ui.utils.InfiniteScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffShowsFragment : Fragment(), StaffShowsAdapter.StaffShowClickHandler {
    private var _bindings: FragmentStaffShowListBinding? = null
    private val vm: StaffShowsViewModel by viewModels()

    private val bindings get() = _bindings!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindings = FragmentStaffShowListBinding.inflate(layoutInflater, container, false)

        val listAdapter = StaffShowsAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        bindings.staffShowList.apply {
            adapter = listAdapter
            layoutManager = recyclerLayoutManager
            addOnScrollListener(InfiniteScrollListener(vm::load, recyclerLayoutManager))
        }

        vm.shows.observe(viewLifecycleOwner, listAdapter::update)

        return bindings.root
    }

    override fun onShowClick(show: StaffShowListItemModel) {
        findNavController().navigate(StaffShowsFragmentDirections.navToDetail(show.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindings = null
    }
}