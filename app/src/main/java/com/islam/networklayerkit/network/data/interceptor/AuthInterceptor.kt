package com.islam.networklayerkit.network.data.interceptor

import com.islam.networklayerkit.network.domain.repository.TokenDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenDataSource: TokenDataSource
) : Interceptor {

    private val skipAuthPaths = setOf(
        "api/register",
        "api/login",
        "api/SendLoginOTP",
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val path = original.url.encodedPath

        // 2) If this path is in our “skip” list, go straight through
        if (path.shouldSkipAuth()) {
            return chain.proceed(original)
        }

        val token = runBlocking { tokenDataSource.getToken() }
            .takeIf { it.isNotBlank() }

        val request = token?.let {
            original.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        } ?: original

        return chain.proceed(request)
    }

    private fun String.shouldSkipAuth(): Boolean =
        skipAuthPaths.any { endsWith(it, ignoreCase = true) }
}