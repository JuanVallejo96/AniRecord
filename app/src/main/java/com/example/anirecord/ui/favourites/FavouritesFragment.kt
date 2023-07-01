package com.example.anirecord.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentFavouritesBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.ui.adapters.SeriesListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(), SeriesListAdapter.SeriesClickHandler {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    val vm: FavouritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        val listAdapter = SeriesListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.favouritesShowList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
        }

        vm.shows.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) {
                binding.favouritesEmptyLabel.visibility = View.VISIBLE
                binding.favouritesShowList.visibility = View.GONE
            } else {
                binding.favouritesEmptyLabel.visibility = View.GONE
                binding.favouritesShowList.visibility = View.VISIBLE
                listAdapter.replaceAll(items)
            }
        }

        return binding.root
    }

    override fun onShowClick(show: ShowListItemModel) {
        findNavController().navigate(FavouritesFragmentDirections.navToDetail(show.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}