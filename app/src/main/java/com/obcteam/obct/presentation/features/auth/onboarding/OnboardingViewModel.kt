package com.obcteam.obct.presentation.features.auth.onboarding

import androidx.lifecycle.ViewModel
import com.obcteam.obct.domain.mvi.MVI
import com.obcteam.obct.domain.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel(),
    MVI<OnboardingState, OnboardingAction, OnboardingSideEffect> by mvi(
        OnboardingState(weight = 65)
    ) {

    override fun onAction(uiAction: OnboardingAction) {
        when (uiAction) {
            is OnboardingAction.SetHeight -> {
                updateUiState { copy(height = uiAction.height) }
            }

            is OnboardingAction.SetWeight -> {
                updateUiState { copy(weight = uiAction.weight) }
            }

            is OnboardingAction.Submit -> {

            }
        }
    }

}