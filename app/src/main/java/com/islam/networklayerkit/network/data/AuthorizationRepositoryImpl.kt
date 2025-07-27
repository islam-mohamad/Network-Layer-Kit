package com.islam.networklayerkit.network.data

import com.islam.networklayerkit.network.domain.AuthEvents
import com.islam.networklayerkit.network.domain.repository.AuthorizationRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor() : AuthorizationRepository {
    override suspend fun observeAuthorization(): SharedFlow<Unit> = AuthEvents.authEvents
}