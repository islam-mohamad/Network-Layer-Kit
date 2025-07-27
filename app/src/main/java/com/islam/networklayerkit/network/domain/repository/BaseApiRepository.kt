package com.islam.networklayerkit.network.domain.repository

import com.islam.networklayerkit.network.domain.model.BaseResponse

interface BaseApiRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResponse<T>): T
}