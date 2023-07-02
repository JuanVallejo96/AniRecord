package com.example.anirecord.ui.listdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.R
import com.example.anirecord.databinding.FragmentListDetailBinding
import com.example.anirecord.domain.model.ShowListItemModel
import com.example.anirecord.ui.adapters.ListDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListDetailFragment : Fragment(), ListDetailAdapter.ListDetailItemClickHandler {
    private var _binding: FragmentListDetailBinding? = null
    private val binding get() = _binding!!

    private val vm: ListDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListDetailBinding.inflate(inflater, container, false)

        val listAdapter = ListDetailAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.ListDetailShowList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listAdapter
        }

        vm.shows.observe(viewLifecycleOwner) {
            listAdapter.replaceAll(it)
            if (it.isEmpty()) {
                binding.EmptyListLabel.visibility = View.VISIBLE
                binding.ListDetailShowList.visibility = View.GONE
            } else {
                binding.EmptyListLabel.visibility = View.GONE
                binding.ListDetailShowList.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onClick(show: ShowListItemModel) {
        findNavController().navigate(ListDetailFragmentDirections.navToDetail(show.id))
    }

    override fun onLongClick(view: View, show: ShowListItemModel) {
        PopupMenu(context, view).apply {
            inflate(R.menu.series_list_popup_menu)
            setOnMenuItemClickListener {
                onPopupItemMenu(it, show)
            }
            show()
        }
    }

    private fun onPopupItemMenu(menuItem: MenuItem, show: ShowListItemModel): Boolean {
        return when (menuItem.itemId) {
            R.id.delete_show_from_list -> {
                vm.deleteShowFromList(show.id)
                true
            }

            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}