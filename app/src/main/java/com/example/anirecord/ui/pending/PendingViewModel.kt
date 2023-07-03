package com.example.anirecord.ui.pending

import androidx.lifecycle.ViewModel
import com.example.anirecord.domain.usecase.GetPendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PendingViewModel @Inject constructor(
    getPendingUseCase: GetPendingUseCase
) : ViewModel() {
    val shows = getPendingUseCase()
}