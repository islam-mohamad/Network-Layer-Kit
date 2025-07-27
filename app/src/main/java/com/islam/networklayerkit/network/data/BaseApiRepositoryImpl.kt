package com.islam.networklayerkit.network.data

import android.content.Context
import com.islam.networklayerkit.R
import com.islam.networklayerkit.network.domain.model.ApiException
import com.islam.networklayerkit.network.domain.model.BaseResponse
import com.islam.networklayerkit.network.domain.model.EmptyResponseException
import com.islam.networklayerkit.network.domain.model.NetworkException
import com.islam.networklayerkit.network.domain.model.ServerException
import com.islam.networklayerkit.network.domain.model.TimeoutException
import com.islam.networklayerkit.network.domain.model.UnauthorizedException
import com.islam.networklayerkit.network.domain.repository.BaseApiRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class BaseApiRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : BaseApiRepository {
    override suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResponse<T>): T {
        try {
            return withContext(Dispatchers.IO) {
                val response = apiCall()
                processResponse(response)
            }
        } catch (e: Exception) {
            throw mapException(e)
        }
    }

    private fun <T> processResponse(response: BaseResponse<T>): T {
        return when (response.code) {
            401 -> throw UnauthorizedException(
                context.getString(R.string.error_unauthorized)
            )

            in 402..599 -> throw ServerException(
                response.message.ifEmpty { context.getString(R.string.error_server) }
            )

            else -> handleResponseData(response)
        }
    }

    private fun <T> handleResponseData(response: BaseResponse<T>): T {
        return if (response.success) {
            response.data ?: throw EmptyResponseException(
                context.getString(R.string.error_success_no_data)
            )
        } else {
            throw ApiException(
                response.message.ifEmpty { context.getString(R.string.error_unknown) }
            )
        }
    }

    private fun mapException(exception: Exception): Exception {
        return when (exception) {
            is SocketTimeoutException -> TimeoutException(
                context.getString(R.string.error_timeout),
                exception
            )

            is IOException -> NetworkException(
                context.getString(R.string.error_network),
                exception
            )

            is HttpException -> mapHttpError(exception)
            is UnauthorizedException,
            is ServerException,
            is NetworkException,
            is TimeoutException,
            is EmptyResponseException,
            is ApiException -> exception

            else -> ApiException(
                exception.message ?: context.getString(R.string.error_unknown),
                exception
            )
        }
    }

    private fun mapHttpError(exception: HttpException): Exception {
        return when (exception.code()) {
            401 -> UnauthorizedException(
                context.getString(R.string.error_unauthorized),
                exception
            )

            in 402..599 -> ServerException(
                context.getString(R.string.error_server),
                exception
            )

            else -> ApiException(
                context.getString(R.string.error_unknown),
                exception
            )
        }
    }
}