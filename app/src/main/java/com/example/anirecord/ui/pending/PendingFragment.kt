package com.example.anirecord.ui.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentPendingBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.ui.adapters.SeriesListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingFragment : Fragment(), SeriesListAdapter.SeriesClickHandler {
    private var _binding: FragmentPendingBinding? = null
    private val binding get() = _binding!!

    val vm: PendingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendingBinding.inflate(inflater, container, false)

        val listAdapter = SeriesListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.pendingShowList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
        }

        vm.shows.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) {
                binding.pendingEmptyLabel.visibility = View.VISIBLE
                binding.pendingShowList.visibility = View.GONE
            } else {
                binding.pendingEmptyLabel.visibility = View.GONE
                binding.pendingShowList.visibility = View.VISIBLE
                listAdapter.replaceAll(items)
            }
        }

        return binding.root
    }

    override fun onShowClick(show: ShowListItemModel) {
        findNavController().navigate(PendingFragmentDirections.navToDetail(show.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}