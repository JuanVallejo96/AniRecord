package com.example.anirecord.ui.listcollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anirecord.R
import com.example.anirecord.databinding.FragmentListCollectionBinding
import com.example.anirecord.databinding.ListCollectionBottomSheetBinding
import com.example.anirecord.domain.model.ListCollectionItemModel
import com.example.anirecord.ui.adapters.ListCollectionAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListCollectionFragment : Fragment(), ListCollectionAdapter.ListCollectionClickHandler {
    private var _binding: FragmentListCollectionBinding? = null
    private val binding get() = _binding!!

    private val vm: ListCollectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListCollectionBinding.inflate(inflater, container, false)

        val listCollectionAdapter = ListCollectionAdapter(this)
        val recyclerLayoutManager = LinearLayoutManager(context)
        binding.listList.apply {
            layoutManager = recyclerLayoutManager
            adapter = listCollectionAdapter
        }

        vm.lists.observe(viewLifecycleOwner, listCollectionAdapter::replaceAll)

        binding.addFab.setOnClickListener {
            showCreateDialog()
        }

        return binding.root
    }

    override fun onClick(list: ListCollectionItemModel) {
        findNavController().navigate(ListCollectionFragmentDirections.navToList(list.id, list.name))
    }

    override fun onLongClick(view: View, list: ListCollectionItemModel) {
        PopupMenu(context, view).apply {
            inflate(R.menu.list_popup_menu)
            setOnMenuItemClickListener {
                onPopupItemMenu(it, list)
            }
            show()
        }
    }

    private fun onPopupItemMenu(menuItem: MenuItem, list: ListCollectionItemModel): Boolean {
        return when (menuItem.itemId) {
            R.id.rename_list -> {
                return showRenameDialog(list)
            }

            R.id.delete_list -> {
                vm.deleteList(list.id)
                true
            }

            else -> false
        }
    }

    private fun showRenameDialog(list: ListCollectionItemModel): Boolean {
        val dialog = BottomSheetDialog(requireContext())
        val dialogLayoutInflater = dialog.layoutInflater
        val dialogBindings = ListCollectionBottomSheetBinding.inflate(
            dialogLayoutInflater,
            binding.root,
            false,
        )

        dialog.setContentView(dialogBindings.root)
        dialogBindings.addListTitle.setText(list.name)
        dialogBindings.addButton.text = requireContext().getText(R.string.update_button_label)
        dialogBindings.addButton.setOnClickListener {
            val name = dialogBindings.addListTitle.text.toString()
            if (vm.updateList(list, name)) {
                dialog.cancel()
            } else {
                Toast.makeText(requireContext(), R.string.wrong_list_name, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.setCancelable(true)
        dialog.show()
        return true
    }

    private fun showCreateDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogLayoutInflater = dialog.layoutInflater
        val dialogBindings = ListCollectionBottomSheetBinding.inflate(
            dialogLayoutInflater,
            binding.root,
            false,
        )

        dialog.setContentView(dialogBindings.root)
        dialogBindings.addButton.setOnClickListener {
            val name = dialogBindings.addListTitle.text.toString()
            if (vm.addList(name)) {
                dialog.cancel()
            } else {
                Toast.makeText(requireContext(), R.string.wrong_list_name, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.setCancelable(true)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}