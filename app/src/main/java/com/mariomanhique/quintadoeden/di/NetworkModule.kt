package com.mariomanhique.quintadoeden.di

import com.mariomanhique.quintadoeden.data.repository.network.NetworkRepository
import com.mariomanhique.quintadoeden.data.repository.network.NetworkRepositoryImpl
import com.mariomanhique.quintadoeden.sync.FcmApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideFcmApi(): FcmApi {
       return Retrofit
            .Builder()
           .baseUrl("https://10.0.2.2:8080/") // On a real device we can using the wifi router
           .addConverterFactory(MoshiConverterFactory.create())
           .build()
           .create(FcmApi::class.java)

    }

    @Provides
    @Singleton
    fun provideNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository = impl
}