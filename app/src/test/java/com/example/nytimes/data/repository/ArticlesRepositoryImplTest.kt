package com.example.nytimes.data.repository

import com.example.nytimes.BaseTest
import com.example.nytimes.BuildConfig
import com.example.nytimes.data.common.utils.DataState
import com.example.nytimes.data.common.utils.WrappedListResponse
import com.example.nytimes.data.remote.api.ArticlesApi
import com.example.nytimes.domain.entity.ArticleItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class ArticlesRepositoryImplTest : BaseTest() {
    private val articlesApi = Mockito.mock(ArticlesApi::class.java)
    private val articleRepository = ArticlesRepositoryImpl(articlesApi)


    @Test
    fun `when article list api is successful`() = runBlockingTest {
        val response = Response.success(WrappedListResponse(data = arrayListOf<ArticleItem>()))
        Mockito.`when`(articlesApi.getPopularArticles(7, BuildConfig.API_KEY))
            .thenReturn(response.body())
        // When
        articleRepository.getArticlesList(7, BuildConfig.API_KEY).collect {
            val result = (it as DataState.Success).value
            Assert.assertSame(response.body(), result)
        }
    }

}
