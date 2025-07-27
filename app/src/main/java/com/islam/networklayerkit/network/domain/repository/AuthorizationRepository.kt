package com.islam.networklayerkit.network.domain.repository

import kotlinx.coroutines.flow.SharedFlow

interface AuthorizationRepository {
    suspend fun observeAuthorization(): SharedFlow<Unit>
}