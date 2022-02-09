package com.example.nytimes.domain

import com.example.nytimes.data.common.utils.DataState
import com.example.nytimes.data.common.utils.WrappedListResponse
import com.example.nytimes.domain.entity.ArticleItem
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun getArticlesList(period : Int , apiKey : String): Flow<DataState<WrappedListResponse<ArticleItem>>>
}