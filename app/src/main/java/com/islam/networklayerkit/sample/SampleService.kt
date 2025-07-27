package com.islam.networklayerkit.sample

import com.islam.networklayerkit.network.domain.model.BaseResponse
import retrofit2.http.GET

interface SampleService {
    @GET
    suspend fun getUser(): BaseResponse<User>
}