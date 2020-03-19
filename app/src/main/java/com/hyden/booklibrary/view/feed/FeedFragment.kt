package com.hyden.booklibrary.view.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.hyojin.util.EndlessRecyclerViewScrollListener
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.databinding.FragmentFeedBinding
import com.hyden.booklibrary.databinding.RecyclerItemFeedBinding
import com.hyden.booklibrary.view.MainActivity
import com.hyden.booklibrary.view.common.LoadingViewModel
import com.hyden.booklibrary.view.detail.SavedDetailViewModel
import com.hyden.ext.onlyNumber
import com.hyden.util.ConstValueUtil.Companion.ITEM_DECORATION
import com.hyden.util.ItemClickListener
import com.hyden.util.RecyclerItemDecoration
import com.hyden.util.toPx
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
                adapter = object : BaseRecyclerView.Adapter<Feed, RecyclerItemFeedBinding>(
                    layoutId = R.layout.recycler_item_feed,
                    bindingVariableId = BR.response,
                    clickItemEvent = itemClickListener
                ) {

                    override fun onViewRecycled(holder: BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>) {
                        super.onViewRecycled(holder)

                        if(holder is BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>) {
                          //  holder.binding?.tvNoteContent?.showMore()
                        }
                    }

                    override fun onViewDetachedFromWindow(holder: BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>) {
                        super.onViewDetachedFromWindow(holder)
//                        if(holder is BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>) {
//                            holder.binding?.tvNoteContent?.isEnabled = false
//                            holder.binding?.tvNoteContent?.showMore()
//                        }
                    }


                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding> {
                        val holder = super.onCreateViewHolder(parent, viewType)
                        clickLike(holder)
                        clickComment(holder)
//                        holder.binding?.setVariable(BR.firestore,feedViewModel.getFireStore())
                        return holder
                    }

                    // 좋아요 기능
                    override fun onBindViewHolder(
                        holder: BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>,
                        position: Int
                    ) {
                        super.onBindViewHolder(holder, position)

                        holder.binding?.viewmodel= feedViewModel
                        holder.binding?.pos= position
                 /*       feedViewModel._feedItems.value?.let { feedItems ->
                            // 게시글마다 로그인한 유저가 좋아요를 클릭한 유저인지 검사.
                            // 좋아요를 클릭한 유저라면 heart를 빨간색으로 표시
//                            holder.binding?.tvNoteContent?.showMore()

                            holder.binding?.ivLike?.isSelected = feedItems[position].likesInfo.users?.let {
                                feedViewModel.isContainsUser(it)
                            } ?: false

                        }*/
                    }
                }
                addOnScrollListener(endlessListener)
                addItemDecoration(RecyclerItemDecoration(ITEM_DECORATION))
            }

            srvlRefresh.apply {
                setOnRefreshListener {
                    feedViewModel.getFireStore()
                    endlessListener.resetState()
                    feedViewModel._feedItems.observe(this@FeedFragment,
                        Observer { isRefreshing = false })
                }
            }
        }

    }

    /**
     * 좋아요 클릭 이벤트
     */
    private fun clickLike(holder: BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>) {
        holder.binding?.apply {
            feedViewModel._feedItems.observe(this@FeedFragment,
                Observer {
                    ivLike?.setOnClickListener { view ->
                        view.isSelected = view.isSelected.not()
                        feedViewModel.pushLiked(
                            holder.adapterPosition,
                            view.isSelected
                        )
                        feedViewModel.isSharedUser.value?.let {
                            if (it) savedDetailViewModel.bookUpdate(feedViewModel._feedItems.value!![holder.adapterPosition].bookEntity)
                        }

                        when (view.isSelected) {
                            true -> tvLikeCount.text =
                                String.format(getString(R.string.like_count),tvLikeCount.text.toString().onlyNumber().toInt() + 1)
                            false -> tvLikeCount.text =
                                String.format(getString(R.string.like_count),tvLikeCount.text.toString().onlyNumber().toInt() - 1)
                        }
                    }
                    // 로딩바 취소
//                    loadingViewModel.hide()
                    (activity as? MainActivity)?.hideLoadingBar()
                }
            )


        }
    }

    /**
     * 댓글 클릭 이벤트
     */
    private fun clickComment(holder: BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>) {
        holder.binding?.apply {
            ivComment?.setOnClickListener {
                Toast.makeText(context, "댓글기능을 준비중입니다.", Toast.LENGTH_SHORT).show()
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