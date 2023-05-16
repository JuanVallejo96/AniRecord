package com.example.anirecord.ui.showdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.anirecord.databinding.FragmentShowDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val vm: ShowDetailViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)

        vm.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ShowDetailViewModel.UiState.Loading -> {
                    //TODO("Spinner (?)")
                }

                is ShowDetailViewModel.UiState.Success -> {
                    //val show = uiState.show
                    //TODO("Update view")
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}