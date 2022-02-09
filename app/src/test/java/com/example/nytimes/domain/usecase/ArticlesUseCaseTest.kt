package com.example.nytimes.domain.usecase

import com.example.nytimes.BaseTest
import com.example.nytimes.BuildConfig
import com.example.nytimes.data.common.utils.DataState
import com.example.nytimes.data.common.utils.ErrorData
import com.example.nytimes.data.common.utils.ErrorResponsee
import com.example.nytimes.data.common.utils.WrappedListResponse
import com.example.nytimes.domain.ArticlesRepository
import com.example.nytimes.domain.entity.ArticleItem
import junit.framework.Assert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class ArticlesUseCaseTest : BaseTest(){
    private val articleRepository = Mockito.mock(ArticlesRepository::class.java)
    private val articlesUseCase = ArticlesUseCase(articleRepository)

    @Test
    fun `language list successful`() = runBlocking {

        val response = MutableStateFlow(DataState.Success(WrappedListResponse(data = arrayListOf<ArticleItem>())))

        // When
        Mockito.`when`(articleRepository.getArticlesList(7,BuildConfig.API_KEY)).thenReturn(response)

        // Then
        val result = articlesUseCase.execute(ArticlesUseCase.Params(7,BuildConfig.API_KEY)).first()

        Assert.assertSame(response.value, result)


    }
    @Test
    fun `language list failure`() = runBlocking {

        val response =  MutableStateFlow(DataState.GenericError(400, ErrorResponsee(ErrorData("", ""))))

        // When
        Mockito.`when`(articleRepository.getArticlesList(7,BuildConfig.API_KEY)).thenReturn(response)

        // Then
        val result = articlesUseCase.execute(ArticlesUseCase.Params(7,BuildConfig.API_KEY)).first()
        Assert.assertSame(response.value, result)

    }

}