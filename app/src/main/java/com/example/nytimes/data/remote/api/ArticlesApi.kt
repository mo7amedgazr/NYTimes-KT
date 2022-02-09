package com.example.nytimes.data.remote.api

import com.example.nytimes.data.common.utils.WrappedListResponse
import com.example.nytimes.domain.entity.ArticleItem
import retrofit2.http.*

interface ArticlesApi {

    @GET("viewed/{period}.json")
    suspend fun getPopularArticles(
        @Path("period") period: Int,
        @Query("api-key") apiKey: String
    ): WrappedListResponse<ArticleItem>
}