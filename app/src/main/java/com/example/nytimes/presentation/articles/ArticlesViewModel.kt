package com.example.nytimes.presentation.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytimes.data.common.utils.DataState
import com.example.nytimes.domain.usecase.ArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase
) : ViewModel() {

    private val state = MutableStateFlow<ArticlesStateModel>(ArticlesStateModel.Init)
    val mState: StateFlow<ArticlesStateModel> get() = state


    private fun setLoading() {
        state.value = ArticlesStateModel.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = ArticlesStateModel.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = ArticlesStateModel.ShowToast(message)
    }


    fun getArticlesList(period: Int, apiKey: String) = viewModelScope.launch {
        setLoading()
        articlesUseCase.execute(ArticlesUseCase.Params(period, apiKey)).collect { response ->
            when (response) {
                is DataState.GenericError -> {
                    response.error?.errorResponse?.errorMessage?.let { err -> showToast(err) }
                }
                is DataState.Success -> {
                    response.value.data?.let { result ->
                        state.value = ArticlesStateModel.ArticlesList(result)
                    }
                }
            }
        }
    }

}