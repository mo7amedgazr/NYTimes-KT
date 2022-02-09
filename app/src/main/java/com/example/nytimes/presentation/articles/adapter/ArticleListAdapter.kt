package com.example.nytimes.presentation.articles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimes.databinding.ItemArticleBinding
import com.example.nytimes.domain.entity.ArticleItem

class ArticleListAdapter :
    ListAdapter<ArticleItem, ArticleListAdapter.LanguageViewHolder>(Companion) {

    interface OnItemTap {
        fun onTap(articleItem: ArticleItem)
    }

    fun setItemTapListener(tapListener: OnItemTap) {
        onTapListener = tapListener
    }

    private var onTapListener: OnItemTap? = null
    private var context: Context? = null


    inner class LanguageViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<ArticleItem>() {
        override fun areItemsTheSame(
            oldItem: ArticleItem,
            newItem: ArticleItem
        ): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ArticleItem,
            newItem: ArticleItem
        ): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return LanguageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.article = currentItem
        holder.binding.clickListener = onTapListener
        holder.binding.executePendingBindings()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}