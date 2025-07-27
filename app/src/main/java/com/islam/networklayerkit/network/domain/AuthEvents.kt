package com.islam.networklayerkit.network.domain

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEvents {
    private val _authEvents = MutableSharedFlow<Unit>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun emitUnauthorized() {
        _authEvents.emit(Unit)
    }
}