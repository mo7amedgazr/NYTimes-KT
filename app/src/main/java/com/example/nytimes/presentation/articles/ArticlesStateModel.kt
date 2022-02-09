package com.example.nytimes.presentation.articles

import com.example.nytimes.data.common.response.BaseResponse
import com.example.nytimes.domain.entity.ArticleItem

sealed class ArticlesStateModel {
    object Init : ArticlesStateModel()
    data class IsLoading(val isLoading: Boolean) : ArticlesStateModel()
    data class ShowToast(val message: String) : ArticlesStateModel()
    data class ArticlesList(val languageList: List<ArticleItem>) : ArticlesStateModel()
    data class Error(val rawResponse: BaseResponse) : ArticlesStateModel()
}