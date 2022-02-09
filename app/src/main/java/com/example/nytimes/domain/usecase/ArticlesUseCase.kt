package com.example.nytimes.domain.usecase

import com.example.nytimes.data.common.utils.DataState
import com.example.nytimes.data.common.utils.NetworkHelper
import com.example.nytimes.data.common.utils.WrappedListResponse
import com.example.nytimes.domain.ArticlesRepository
import com.example.nytimes.domain.entity.ArticleItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticlesUseCase @Inject constructor(private val articlesRepository: ArticlesRepository) :
    NetworkHelper<ArticlesUseCase.Params, WrappedListResponse<ArticleItem>>() {

    data class Params constructor(
        val period: Int,
        val apiKey: String
    )

    override suspend fun buildUseCase(parameter: Params): Flow<DataState<WrappedListResponse<ArticleItem>>> {
        return articlesRepository.getArticlesList(parameter.period, parameter.apiKey)
    }
}