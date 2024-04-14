package com.mariomanhique.quintadoeden.di

import com.mariomanhique.quintadoeden.data.repository.authWithCredentials.AuthRepository
import com.mariomanhique.quintadoeden.data.repository.authWithCredentials.AuthRepositoryImpl
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepository
import com.mariomanhique.quintadoeden.data.repository.firestore.FirestoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository = authRepositoryImpl

    @Provides
    @Singleton
    fun provideFirestoreRepository(firestoreRepositoryImpl: FirestoreRepositoryImpl): FirestoreRepository = firestoreRepositoryImpl
}