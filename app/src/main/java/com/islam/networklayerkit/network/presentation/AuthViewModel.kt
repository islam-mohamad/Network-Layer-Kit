package com.islam.networklayerkit.network.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.networklayerkit.network.domain.usecase.ObserveAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val observeAuthUseCase: ObserveAuthUseCase
) : ViewModel() {

    //Observe To Nav To Login Screen
    private val expiredState = MutableSharedFlow<Unit>()
    val expiredFlow: SharedFlow<Unit> = expiredState.asSharedFlow()

    init {
        viewModelScope.launch {
            observeAuthUseCase()
                .collectLatest {
                    extendSession()
                }
        }
    }

    fun performLogout() {

    }

    fun extendSession() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                performLogout()
                expiredState.emit(Unit)
            }
        }
    }
}