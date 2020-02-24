package com.hyden.booklibrary.view.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
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
import com.hyden.booklibrary.util.ConstUtil.Companion.DEFAULT_COLLAPSEDLINES
import com.hyden.booklibrary.view.detail.SavedDetailViewModel
import com.hyden.util.ConstValueUtil.Companion.ITEM_DECORATION
import com.hyden.util.ItemClickListener
import com.hyden.util.LogUtil.LogE
import com.hyden.util.RecyclerItemDecoration
import com.hyden.util.toPx
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

//    private val firestore by lazy { FirebaseFirestore.getInstance() }
//    private val item by lazy { mutableListOf<BookEntity>() }
//    lateinit var documents: List<DocumentSnapshot>

    private val feedViewModel by viewModel<FeedViewModel>()
    private val savedDetailViewModel by viewModel<SavedDetailViewModel>()


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
            rvBookFeed.apply {
                addItemDecoration(RecyclerItemDecoration(5f.toPx(context)))
                adapter = object : BaseRecyclerView.Adapter<Feed, RecyclerItemFeedBinding>(
                    layoutId = R.layout.recycler_item_feed,
                    bindingVariableId = BR.response,
                    clickItemEvent = itemClickListener
                ) {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding> {
                        val holder = super.onCreateViewHolder(parent, viewType)
                        holder.binding?.apply {
                            feedViewModel.feedItems.observe(this@FeedFragment,
                                Observer {
                                    ivLike?.setOnClickListener { view ->
                                        view.isSelected = view.isSelected.not()
                                        feedViewModel.pushLiked(
                                            holder.adapterPosition,
                                            view.isSelected
                                        )
                                        feedViewModel.isSharedUser.value?.let {
                                            if (it) savedDetailViewModel.bookUpdate(feedViewModel.feedItems.value!![holder.adapterPosition].bookEntity)
                                        }
                                        when (view.isSelected) {
                                            true -> tvLikeCount.text =
                                                (tvLikeCount.text.toString().toInt() + 1).toString()
                                            false -> tvLikeCount.text =
                                                (tvLikeCount.text.toString().toInt() - 1).toString()
                                        }
                                    }
                                }
                            )

                            ivComment?.setOnClickListener {
                                Toast.makeText(context, "댓글", Toast.LENGTH_SHORT).show()
                            }
                        }
                        return holder
                    }

                    // 좋아요 기능
                    override fun onBindViewHolder(
                        holder: BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>,
                        position: Int
                    ) {
                        super.onBindViewHolder(holder, position)

//                        holder.binding?.tvExpandable?.viewTreeObserver?.addOnGlobalLayoutListener {
//                            LogE("position : ${holder.adapterPosition}")
//                            LogE("line count : ${holder.binding?.tvExpandable?.lineCount}")
//                            LogE("title : ${holder.binding?.tvTitle?.text}")
//                            LogE("content : ${holder.binding?.tvExpandable?.text}")
//                            LogE("----------------------------------------------------------")
//                            if (holder.binding?.tvExpandable?.lineCount!! < DEFAULT_COLLAPSEDLINES)
//                                holder.binding?.tvShowMore?.visibility = View.INVISIBLE
//                        }
//                        LogE("position : ${holder.adapterPosition}")
//                        LogE("line count : ${holder.binding?.tvExpandable?.lineCount}")
//                        LogE("title : ${holder.binding?.tvTitle?.text}")
//                        LogE("content : ${holder.binding?.tvExpandable?.text}")
//                        LogE("----------------------------------------------------------")
//                        holder.binding?.tvExpandable?.viewTreeObserver?.addOnGlobalLayoutListener(
//                            object : ViewTreeObserver.OnGlobalLayoutListener {
//                                override fun onGlobalLayout() {
////                                    LogE("test : ${holder.binding?.tvExpandable?.height}")
////                                    LogE("position : ${holder.adapterPosition}")
////                                    LogE("line count : ${holder.binding?.tvExpandable?.lineCount}")
////                                    LogE("title : ${holder.binding?.tvTitle?.text}")
////                                    LogE("content : ${holder.binding?.tvExpandable?.text}")
////                                    LogE("----------------------------------------------------------")
//                                    if (holder.binding?.tvExpandable?.lineCount!! > DEFAULT_COLLAPSEDLINES)
//                                        holder.binding?.tvShowMore?.visibility = View.VISIBLE
//                                    holder.binding?.tvExpandable?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
//                                }
//                            }
//                        )
                        feedViewModel.feedItems.value?.let { feedItems ->
                            //                            holder.binding?.ivLike?.isSelected = it[position].likesInfo.users?.contains(User(LOGIN_ID, LOGIN_NAME)) ?: false
                            holder.binding?.ivLike?.isSelected =
                                feedItems[position].likesInfo.users?.let {
                                    feedViewModel.isContainsUser(it)
                                } ?: false
                        }
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