package com.hyden.booklibrary.view.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.data.model.User
import com.hyden.booklibrary.databinding.FragmentFeedBinding
import com.hyden.booklibrary.databinding.RecyclerItemFeedBinding
import com.hyden.booklibrary.view.detail.SavedDetailViewModel
import com.hyden.util.ConstValueUtil.Companion.ITEM_DECORATION
import com.hyden.util.ItemClickListener
import com.hyden.util.RecyclerItemDecoration
import org.koin.android.ext.android.inject

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

//    private val firestore by lazy { FirebaseFirestore.getInstance() }
//    private val item by lazy { mutableListOf<BookEntity>() }
//    lateinit var documents: List<DocumentSnapshot>

    private val feedViewModel by inject<FeedViewModel>()
    private val savedDetailViewModel by inject<SavedDetailViewModel>()


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
                                        feedViewModel.pushLiked(holder.adapterPosition, view.isSelected)
                                        feedViewModel.isSharedUser.value?.let {
                                            if(it) savedDetailViewModel.bookUpdate(feedViewModel.feedItems.value!![holder.adapterPosition].bookEntity)
                                        }
                                    }
                                }
                            )

                            ivChat?.setOnClickListener {
                                Toast.makeText(context, "댓글", Toast.LENGTH_SHORT).show()
                            }
                        }
                        return holder
                    }

                    override fun onBindViewHolder(
                        holder: BaseRecyclerView.ViewHolder<RecyclerItemFeedBinding>,
                        position: Int
                    ) {
                        super.onBindViewHolder(holder, position)
                        feedViewModel.feedItems.value?.let { feedItems ->
//                            holder.binding?.ivLike?.isSelected = it[position].likesInfo.users?.contains(User(LOGIN_ID, LOGIN_NAME)) ?: false
                            holder.binding?.ivLike?.isSelected = feedItems[position].likesInfo.users?.let { feedViewModel.isContainsUser(it) } ?: false
                        }

                    }
                }
                addItemDecoration(RecyclerItemDecoration(ITEM_DECORATION))
            }

            srvlRefresh.apply {
                setOnRefreshListener {
                    feedViewModel.getFireStore()
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