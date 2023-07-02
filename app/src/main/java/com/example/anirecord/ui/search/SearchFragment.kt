package com.example.anirecord.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.R
import com.example.anirecord.databinding.FragmentSearchBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.ui.adapters.SeriesListAdapter
import com.example.anirecord.utils.InfiniteScrollListener
import com.example.anirecord.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class SearchFragment : Fragment(), SeriesListAdapter.SeriesClickHandler {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val vm: SearchViewModel by viewModels()

    private val queryListener: QueryListener by lazy {
        QueryListener(vm::search, lifecycleScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val listAdapter = SeriesListAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.seriesList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
            addOnScrollListener(InfiniteScrollListener(vm::keepLoading, recyclerLayoutManager))
        }

        vm.uiState.observe(viewLifecycleOwner) { uiState ->
            val (list, startLabel, emptyLabel) = when (uiState) {
                is SearchViewModel.UiState.Success -> {
                    if (uiState.newSearch) {
                        listAdapter.replaceAll(uiState.shows)
                    } else {
                        listAdapter.update(uiState.shows)
                    }
                    Triple(View.VISIBLE, View.GONE, View.GONE)
                }

                is SearchViewModel.UiState.Start -> Triple(View.GONE, View.VISIBLE, View.GONE)

                is SearchViewModel.UiState.Empty -> Triple(View.GONE, View.GONE, View.VISIBLE)
            }

            binding.seriesList.visibility = list
            binding.searchStartLabel.visibility = startLabel
            binding.searchEmptyLabel.visibility = emptyLabel
        }

        requireActivity().addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_fragment_menu, menu)
                val searchButton = menu.findItem(R.id.search_button).actionView as? SearchView
                searchButton?.setOnQueryTextListener(queryListener)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search_button -> true
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onShowClick(show: ShowListItemModel) {
        findNavController().navigate(SearchFragmentDirections.navToDetail(show.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class QueryListener(
        onChanged: (String) -> Unit,
        scope: CoroutineScope,
    ) : SearchView.OnQueryTextListener {
        private val debouncer = Utils.debounce(300, scope, onChanged)

        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                debouncer(query)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null) {
                debouncer(newText)
            }
            return true
        }
    }
}
