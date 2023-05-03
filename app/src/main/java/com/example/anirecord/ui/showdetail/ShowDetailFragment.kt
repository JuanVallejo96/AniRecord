package com.example.anirecord.ui.showdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.anirecord.databinding.FragmentShowDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ShowDetailFragment : Fragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val args: ShowDetailFragmentArgs by navArgs()
    private val vm: ShowDetailViewModel by viewModel { parametersOf(args.showId) }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)

        vm.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ShowDetailUiState.Loading -> {
                    //TODO("Spinner (?)")
                }

                is ShowDetailUiState.Success -> {
                    val show = uiState.show
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