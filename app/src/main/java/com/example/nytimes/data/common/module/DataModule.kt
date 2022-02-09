package com.example.nytimes.data.common.module

import com.example.nytimes.data.repository.ArticlesRepositoryImpl
import com.example.nytimes.domain.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun bindArticles(articlesRepositoryImpl: ArticlesRepositoryImpl): ArticlesRepository
}