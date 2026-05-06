package com.farhanisty.noi.di

import com.farhanisty.noi.data.repository.MatkulRepositoryImpl
import com.farhanisty.noi.domain.repository.MatkulRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMatkulRepository(impl: MatkulRepositoryImpl): MatkulRepository
}
