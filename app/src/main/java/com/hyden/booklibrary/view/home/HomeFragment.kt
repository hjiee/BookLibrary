package com.hyden.booklibrary.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hyojin.util.EndlessRecyclerViewScrollListener
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.databinding.FragmentHomeBinding
import com.hyden.booklibrary.databinding.RecyclerItemHomeBinding
import com.hyden.util.LogUtil.LogW
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by inject<HomeViewModel>()

    private val endlessListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding.rvBook.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                homeViewModel.loadMore(page = page+1)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.loadBook()
    }

    override fun initBind() {
        binding.apply {
            vm = homeViewModel
            rvBook.apply {
                adapter = object : BaseRecyclerView.Adapter<BookResponse, RecyclerItemHomeBinding, Any>(
                    layoutId = R.layout.recycler_item_home,
                    bindingVariableId = BR.response,
                    event = {
                        LogW(it.toString())
//                        Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
                    }
                ) {}
                addOnScrollListener(endlessListener)
            }
            rvRefresh.apply {
                setOnRefreshListener {
                    homeViewModel.loadRefresh()
                    homeViewModel.isRefreshing.observe(
                        this@HomeFragment,
                        Observer {
                            when(it) {
                                true -> {}
                                false -> isRefreshing = false
                            }
                        }
                    )
                }
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}