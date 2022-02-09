package com.example.nytimes.presentation.articles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nytimes.BuildConfig
import com.example.nytimes.R
import com.example.nytimes.databinding.FragmentArticleListBinding
import com.example.nytimes.domain.entity.ArticleItem
import com.example.nytimes.presentation.articles.adapter.ArticleListAdapter
import com.example.nytimes.presentation.base.BaseFragment
import com.example.nytimes.utils.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ArticlesListFragment : BaseFragment() {

    private var _binding: FragmentArticleListBinding? = null

    private val mViewModel: ArticlesViewModel by activityViewModels()

    private val mAdapter by lazy {
        ArticleListAdapter()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        initViewModel()

    }

    private fun initRV() {
        binding.rvArticles.setHasFixedSize(true)
        binding.rvArticles.adapter = mAdapter
        binding.rvArticles.addItemDecoration(VerticalSpaceItemDecoration(20, false))
        mAdapter.setItemTapListener(object : ArticleListAdapter.OnItemTap {
            override fun onTap(articleItem: ArticleItem) {
                // open details page.
                val bundle = bundleOf("article" to articleItem)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        })
    }

    private fun initViewModel() {
        mViewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        mViewModel.getArticlesList(7, BuildConfig.API_KEY)
    }

    private fun handleState(state: ArticlesStateModel) {
        when (state) {
            is ArticlesStateModel.IsLoading -> handleLoading(state.isLoading)
            is ArticlesStateModel.ShowToast -> showToast(state.message)
            is ArticlesStateModel.Init -> Unit
            is ArticlesStateModel.ArticlesList -> handleArticleList(state.languageList)
            else -> {

            }
        }
    }


    private fun handleArticleList(articleList: List<ArticleItem>) {
        handleLoading(isLoading = false)
        mAdapter.submitList(articleList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}