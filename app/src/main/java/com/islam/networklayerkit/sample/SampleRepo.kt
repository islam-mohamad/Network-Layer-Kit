package com.islam.networklayerkit.sample

import com.islam.networklayerkit.network.domain.repository.BaseApiRepository
import javax.inject.Inject

class SampleRepo @Inject constructor(
    private val service: SampleService,
    private val baseRepository: BaseApiRepository
) :
    BaseApiRepository by baseRepository {
    suspend fun getUser(): User {
        return safeApiCall {
            service.getUser()
        }
    }
}