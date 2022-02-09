package com.example.nytimes.presentation.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nytimes.BuildConfig
import com.example.nytimes.CoroutineDispatcherRule
import com.example.nytimes.data.common.response.BaseResponse
import com.example.nytimes.data.common.utils.DataState
import com.example.nytimes.data.common.utils.WrappedListResponse
import com.example.nytimes.domain.ArticlesRepository
import com.example.nytimes.domain.entity.ArticleItem
import com.example.nytimes.domain.usecase.ArticlesUseCase
import junit.framework.TestCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ArticlesViewModelTest : TestCase(){


    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = CoroutineDispatcherRule()

    private val articlesRepository = Mockito.mock(ArticlesRepository::class.java)


    private val articleListUseCase = ArticlesUseCase(articlesRepository)


    private var articleViewModel: ArticlesViewModel? = null
    private val state = MutableStateFlow<ArticlesStateModel>(ArticlesStateModel.Init)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        articleViewModel = ArticlesViewModel(
            articleListUseCase
        )

    }

    @Test
    fun `when article list successful  - getArticleList()`() = runBlocking {
        Assert.assertEquals(articleViewModel?.mState?.first(), ArticlesStateModel.Init)
        val articleList = mutableListOf<ArticleItem>()
        articleList.add(ArticleItem())
        val response = WrappedListResponse(data = articleList)
        val channel = Channel<DataState<WrappedListResponse<ArticleItem>>>()
        val flow = channel.consumeAsFlow()
        // When
        Mockito.`when`(articleListUseCase.execute(ArticlesUseCase.Params(7,BuildConfig.API_KEY)))
            .thenReturn(flow)

        // Then
        state.value = response.data?.let { ArticlesStateModel.ArticlesList(it) }!!

        Assert.assertEquals(state.value, ArticlesStateModel.ArticlesList(response.data!!))


    }


//
    @Test
    fun `when loading - isLoading()`() = runBlocking {
        // Then
        state.value = ArticlesStateModel.IsLoading(true)

        Assert.assertEquals(state.value, ArticlesStateModel.IsLoading(true))


    }
    @Test
    fun `testing toast is working with state`()= runBlocking{

        state.value =  ArticlesStateModel.ShowToast("Test")

        Assert.assertEquals(state.value, ArticlesStateModel.ShowToast("Test"))

    }
//
    @Test
    fun `when error occurs - Error()`() = runBlocking {
        val baseResponse= BaseResponse()
        state.value =  ArticlesStateModel.Error(baseResponse)
        Assert.assertEquals(state.value, ArticlesStateModel.Error(baseResponse))

    }

    @Test
    fun `when loading true - isLoading()`() = runBlocking {
        // Then
        state.value = ArticlesStateModel.IsLoading(true)

        Assert.assertEquals(state.value, ArticlesStateModel.IsLoading(true))


    }
}