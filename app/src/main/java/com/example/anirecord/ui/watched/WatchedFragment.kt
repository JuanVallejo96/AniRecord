package com.example.anirecord.ui.watched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.databinding.FragmentWatchedBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.ui.adapters.SeriesListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WatchedFragment @Inject constructor() : Fragment(), SeriesListAdapter.SeriesClickHandler {
    private var _bindings: FragmentWatchedBinding? = null
    private val vm: WatchedViewModel by viewModels()

    private val bindings get() = _bindings!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindings = FragmentWatchedBinding.inflate(inflater, container, false)

        val listAdapter = SeriesListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        bindings.watchedShows.apply {
            adapter = listAdapter
            layoutManager = recyclerLayoutManager
        }

        vm.shows.observe(viewLifecycleOwner) {
            listAdapter.replaceAll(it)
            if (it.isEmpty()) {
                bindings.watchedShows.visibility = View.GONE
                bindings.watchedEmptyLabel.visibility = View.VISIBLE
            } else {
                bindings.watchedShows.visibility = View.VISIBLE
                bindings.watchedEmptyLabel.visibility = View.GONE
            }
        }

        return bindings.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindings = null
    }

    override fun onShowClick(show: ShowListItemModel) {
        findNavController().navigate(WatchedFragmentDirections.navToDetail(show.id))
    }
}