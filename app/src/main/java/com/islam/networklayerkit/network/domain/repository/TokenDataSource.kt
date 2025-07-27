package com.islam.networklayerkit.network.domain.repository

interface TokenDataSource {
    suspend fun getToken(): String
}