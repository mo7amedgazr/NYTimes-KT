package com.example.nytimes.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MediaMetadataItem(

	@field:SerializedName("format")
	val format: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
):Serializable