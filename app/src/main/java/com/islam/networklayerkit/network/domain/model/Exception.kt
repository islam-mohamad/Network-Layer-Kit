package com.islam.networklayerkit.network.domain.model


open class AppException(message: String, cause: Throwable? = null) : Exception(message, cause)
class UnauthorizedException(message: String, cause: Throwable? = null) :
    AppException(message, cause)
class ServerException(message: String, cause: Throwable? = null) : AppException(message, cause)
class NetworkException(message: String, cause: Throwable? = null) : AppException(message, cause)
class TimeoutException(message: String, cause: Throwable? = null) : AppException(message, cause)
class EmptyResponseException(message: String, cause: Throwable? = null) :
    AppException(message, cause)
class ApiException(message: String, cause: Throwable? = null) : AppException(message, cause)