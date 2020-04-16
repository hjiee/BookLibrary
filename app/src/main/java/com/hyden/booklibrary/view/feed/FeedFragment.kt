package com.hyden.booklibrary.view.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.hyojin.util.EndlessRecyclerViewScrollListener
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.FragmentFeedBinding
import com.hyden.booklibrary.databinding.RecyclerItemFeedBinding
import com.hyden.booklibrary.view.MainActivity
import com.hyden.booklibrary.view.common.LoadingViewModel
import com.hyden.booklibrary.view.detail.feed.FeedDetailActivity
import com.hyden.booklibrary.view.detail.mysaved.SavedDetailViewModel
import com.hyden.booklibrary.view.feed.model.FeedData
import com.hyden.ext.moveToActivity
import com.hyden.util.ConstValueUtil.Companion.ITEM_DECORATION
import com.hyden.util.ItemClickListener
import com.hyden.util.RecyclerItemDecoration
import com.hyden.util.toPx
import kotlinx.android.synthetic.main.recycler_item_feed.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

    private val feedViewModel by viewModel<FeedViewModel>()
    private val savedDetailViewModel by viewModel<SavedDetailViewModel>()
    private val loadingViewModel by viewModel<LoadingViewModel>()


    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when (item) {
                    is BookEntity -> {
                        Toast.makeText(context, item.title!!.split(" - ")[0], Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
    private val endlessListener by lazy {
        object : EndlessRecyclerViewScrollListener(binding.rvBookFeed.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                feedViewModel.loadMore()
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

    }

    override fun onResume() {
        super.onResume()
    }

    override fun initBind() {
        feedViewModel.getFireStore()

        binding.apply {
            vm = feedViewModel
            // 로딩바 표시
//            loadingViewModel.show()
            (activity as? MainActivity)?.showLoadingBar()
            rvBookFeed.apply {
                addItemDecoration(RecyclerItemDecoration(5f.toPx(context)))
                adapter = object : BaseRecyclerView.Adapter<FeedData, RecyclerItemFeedBinding>(
                    layoutId = R.layout.recycler_item_feed,
                    bindingVariableId = BR.response,
                    clickItemEvent = itemClickListener
                ) {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding> {
                        val holder = super.onCreateViewHolder(parent, viewType)
                        holder.binding?.setVariable(BR.feedVm,feedViewModel)
                        holder.itemView.etl_note_content.tvContents.setOnClickListener {
//                            Toast.makeText(context, "${holder.adapterPosition}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "${feedViewModel.feedItems.value!![holder.adapterPosition].feed?.bookEntity?.title}", Toast.LENGTH_SHORT).show()
                            Intent(activity,FeedDetailActivity::class.java).run {
                                putExtra(getString(R.string.key_feed_data),feedViewModel.feedItems.value!![holder.adapterPosition].feed?.bookEntity)
                                moveToActivity(this)
                            }
                        }
                        return holder
                    }
                }
                addOnScrollListener(endlessListener)
                addItemDecoration(RecyclerItemDecoration(ITEM_DECORATION))
            }

            srvlRefresh.apply {
                setOnRefreshListener {
                    feedViewModel.getFireStore()
                    endlessListener.resetState()
                    feedViewModel.feedItems.observe(this@FeedFragment,
                        Observer { isRefreshing = false })
                }
            }
        }

    }

    companion object {
        fun newInstance() = FeedFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}