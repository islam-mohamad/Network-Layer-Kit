package com.islam.networklayerkit.network.data.interceptor

import com.islam.networklayerkit.network.domain.AuthEvents
import com.islam.networklayerkit.network.domain.repository.TokenDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UnauthorizedInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            CoroutineScope(Dispatchers.IO).launch {
                AuthEvents.emitUnauthorized()
            }
        }

        return response
    }
}