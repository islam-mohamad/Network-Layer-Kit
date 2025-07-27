package com.islam.networklayerkit.network.di

import com.islam.networklayerkit.BuildConfig
import com.islam.networklayerkit.network.data.AuthorizationRepositoryImpl
import com.islam.networklayerkit.network.domain.repository.BaseApiRepository
import com.islam.networklayerkit.network.data.BaseApiRepositoryImpl
import com.islam.networklayerkit.network.data.interceptor.AuthInterceptor
import com.islam.networklayerkit.network.data.interceptor.UnauthorizedInterceptor
import com.islam.networklayerkit.network.domain.repository.AuthorizationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(
            authInterceptor: AuthInterceptor,
            unauthorizedInterceptor: UnauthorizedInterceptor
        ): OkHttpClient {
            return if (BuildConfig.DEBUG) OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(unauthorizedInterceptor).build() else
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(unauthorizedInterceptor)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.NONE
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Binds
    @Singleton
    abstract fun bindBaseApiRepository(impl: BaseApiRepositoryImpl): BaseApiRepository

    @Binds
    @Singleton
    abstract fun bindAuthorizationRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository
}