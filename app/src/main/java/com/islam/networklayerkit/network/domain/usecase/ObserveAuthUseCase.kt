package com.islam.networklayerkit.network.domain.usecase

import com.islam.networklayerkit.network.domain.repository.AuthorizationRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ObserveAuthUseCase @Inject constructor(private val repository: AuthorizationRepository){
    suspend operator fun invoke(): SharedFlow<Unit> = repository.observeAuthorization()
}