package com.example.anirecord.ui.stafflist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentStaffListBinding
import com.example.anirecord.domain.model.ShowStaffListItemModel
import com.example.anirecord.utils.InfiniteScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffListFragment : Fragment(), StaffListAdapter.StaffClickHandler {
    private var _binding: FragmentStaffListBinding? = null

    private val vm: StaffListViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffListBinding.inflate(inflater, container, false)

        val listAdapter = StaffListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.staffList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
            addOnScrollListener(InfiniteScrollListener(vm::load, recyclerLayoutManager))
        }

        vm.staff.observe(viewLifecycleOwner, listAdapter::update)

        return binding.root
    }

    override fun onStaffClickHandler(staff: ShowStaffListItemModel) {
        findNavController().navigate(
            StaffListFragmentDirections.navToStaffShowsList(staff.id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}