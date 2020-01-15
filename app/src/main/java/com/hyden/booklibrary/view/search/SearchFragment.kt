package com.hyden.booklibrary.view.search

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.hyojin.util.EndlessRecyclerViewScrollListener
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.SearchResponse
import com.hyden.booklibrary.data.remote.network.reponse.toBookEntity
import com.hyden.booklibrary.databinding.FragmentSearchBinding
import com.hyden.booklibrary.databinding.RecyclerItemSearchBinding
import com.hyden.booklibrary.util.QueryType
import com.hyden.booklibrary.view.detail.UnSavedDetailActivity
import com.hyden.ext.moveToActivity
import com.hyden.ext.showKeyboard
import com.hyden.util.ItemClickListener
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private lateinit var searchQuery: String
    private val delayTime: Long = 500L
    private val searchViewModel by inject<SearchViewModel>()

    private val endlessListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding.rvBook.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                showProgress()
                searchViewModel.searchMore(
                    page = page + 1,
                    query = binding.includeAppbar.edtSearchInput.text.toString(),
                    queryType = getQueryType()
                )
            }
        }
    }
    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when (item) {
                    is BookItems -> {
                        Intent(activity, UnSavedDetailActivity::class.java).apply {
                            putExtra(getString(R.string.book_info), item.toBookEntity())
                            moveToActivity(this)
                        }
                    }
                }
            }
        }
    }

    private val runnable by lazy {
        Runnable {
            searchViewModel.searchRefresh(
                query = searchQuery,
                queryType = getQueryType()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 통신 결과에 대해 observing 한다.

        searchViewModel.apply {
            isResultEmpty.observe(
                this@SearchFragment,
                Observer {
                    when (it) {
                        true -> binding.tvResult.visibility = View.GONE
                        false -> binding.tvResult.visibility = View.VISIBLE
                    }
                }
            )
            searchFinishing.observe(
                this@SearchFragment,
                Observer {
                    binding.apply {
                        includeAppbar.apply { if (it) hideProgress() }
                    }
                }
            )
        }
    }


    override fun initBind() {
        binding.apply {
            vm = searchViewModel
            // 리사이클러 뷰
            rvBook.apply {
                adapter = object :
                    BaseRecyclerView.Adapter<SearchResponse, RecyclerItemSearchBinding>(
                        layoutId = R.layout.recycler_item_search,
                        bindingVariableId = BR.search,
                        clickItemEvent = itemClickListener
                    ) {
                }
                addOnScrollListener(endlessListener)

            }
            // 검색 바
            includeAppbar.apply {
                // 검색창
                edtSearchInput.apply {
                    addTextChangedListener {
                        handler.removeCallbacks(runnable)
                        searchQuery = it.toString()
                        handler.postDelayed(runnable, delayTime)
                        // 검색될때 프로그레스바 표시
                        showProgress()
                    }
                }
                // 클리어 버튼
                ibClear.apply {
                    setOnClickListener {
                        edtSearchInput.setText("")
                        showProgress()
                        context.showKeyboard(edtSearchInput)
                        searchViewModel.searchRefresh()
                    }
                }
                // 검색 타입 스피너
                spChoice.apply {
                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(p0: AdapterView<*>?) {}
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            view: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            (view as TextView).setTextColor(Color.WHITE)
                            edtSearchInput.hint = ("${selectedItem} 검색")
                            showProgress()
                            searchViewModel.searchRefresh(
                                query = binding.includeAppbar.edtSearchInput.text.toString(),
                                queryType = getQueryType()
                            )
                        }
                    }
                }
            }
            // 리사이클러뷰 새로고침
            srvlRefresh.apply {
                setOnRefreshListener {
                    showProgress()
                    searchViewModel.searchRefresh(
                        query = binding.includeAppbar.edtSearchInput.text.toString(),
                        queryType = getQueryType()
                    )
                    searchViewModel.isRefreshing.observe(
                        this@SearchFragment,
                        Observer {
                            when (it) {
                                true -> {
                                }
                                false -> isRefreshing = false
                            }
                        }
                    )
                }
            }
        }
    }

    private fun hideProgress() {
        binding.includeAppbar.apply {
            if (edtSearchInput.text.toString().isNullOrEmpty())
                ibClear.visibility = View.INVISIBLE
            else
                ibClear.visibility = View.VISIBLE
            progressbar.visibility = View.INVISIBLE
        }
    }

    private fun showProgress() {
        binding.includeAppbar.apply {
            ibClear.visibility = View.INVISIBLE
            progressbar.visibility = View.VISIBLE
        }
    }

    private fun getQueryType(): String =
        when (binding.includeAppbar.spChoice.selectedItemPosition) {
            0 -> QueryType.KEYWORD.toString()
            1 -> QueryType.TITLE.toString()
            2 -> QueryType.AUTHOR.toString()
            3 -> QueryType.PUBLISHER.toString()
            else -> "keyword"
        }


    companion object {
        fun newInstance() = SearchFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}
