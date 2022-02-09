package com.example.nytimes.data.repository

import com.example.nytimes.data.common.utils.DataState
import com.example.nytimes.data.common.utils.WrappedListResponse
import com.example.nytimes.data.remote.api.ArticlesApi
import com.example.nytimes.domain.ArticlesRepository
import com.example.nytimes.domain.entity.ArticleItem

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val articlesApi: ArticlesApi) : ArticlesRepository {

    override suspend fun getArticlesList(
        period: Int,
        apiKey: String
    ): Flow<DataState<WrappedListResponse<ArticleItem>>> =
        flow {
            emit(DataState.Success(articlesApi.getPopularArticles(period, apiKey)))
        }

}