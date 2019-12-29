package com.hyden.booklibrary.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.hyojin.util.EndlessRecyclerViewScrollListener
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.data.remote.network.reponse.toBookEntity
import com.hyden.booklibrary.databinding.FragmentHomeBinding
import com.hyden.booklibrary.databinding.RecyclerItemHomeBinding
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BESTSELLER
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BLOGBEST
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEW
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEWALL
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASELIMIT
import com.hyden.booklibrary.view.detail.UnSavedDetailActivity
import com.hyden.ext.loadUrl
import com.hyden.ext.moveToActivity
import com.hyden.util.ImageTransformType
import com.hyden.util.ItemClickListener
import com.hyden.util.LogUtil.LogE
import com.hyden.util.LogUtil.LogW
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by inject<HomeViewModel>()
    private val timeInterval = 10000L

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        binding.ivBookCover.apply {
            homeViewModel.bookBlogBest.observe(
                this@HomeFragment,
                Observer {
                    val initRand = Random.nextInt(DATABASELIMIT)
                    binding.ivBookCoverTemp.loadUrl(it[initRand].cover, ImageTransformType.ROUND)
                    loadUrl(it[initRand].cover, ImageTransformType.ROUND)
                    compositeDisposable.add(
                        Observable.interval(timeInterval, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                val rand = Random.nextInt(0, 30)
                                Handler().postDelayed({
                                    binding.ivBookCoverTemp.loadUrl(
                                        homeViewModel.bookBlogBest.value!![rand].cover,
                                        ImageTransformType.ROUND
                                    )
                                }, 3000)
                                LogW("$it : $rand")
                                binding.ivBookCover.loadUrl(
                                    homeViewModel.bookBlogBest.value!![rand].cover,
                                    ImageTransformType.ROUND
                                )
                            }
                    )
                }
            )
        }
    }

    override fun onDestroyView() {
        LogE("onDestroyView")
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        homeViewModel.loadBook()
        homeViewModel.loadBook(page = Random.nextInt(1, 4), queryType = BOOK_BLOGBEST)
        homeViewModel.loadBook(queryType = BOOK_BESTSELLER)
        homeViewModel.loadBook(queryType = BOOK_ITEMNEW)
        homeViewModel.loadBook(queryType = BOOK_ITEMNEWALL)
    }

    override fun initBind() {
        binding.apply {
            vm = homeViewModel
            rvBookBestseller.apply {
                adapter = object :
                    BaseRecyclerView.Adapter<BookResponse, RecyclerItemHomeBinding>(
                        layoutId = R.layout.recycler_item_home,
                        bindingVariableId = BR.response,
                        clickItemEvent = itemClickListener
                    ) {

                }
                addOnScrollListener(endLessScrollListener1(BOOK_BESTSELLER))
            }
            rvBookNew.apply {
                adapter = object :
                    BaseRecyclerView.Adapter<BookResponse, RecyclerItemHomeBinding>(
                        layoutId = R.layout.recycler_item_home,
                        bindingVariableId = BR.response,
                        clickItemEvent = itemClickListener
                    ) {}
                addOnScrollListener(endLessScrollListener2(BOOK_ITEMNEW))
            }
            rvBookAll.apply {
                adapter = object :
                    BaseRecyclerView.Adapter<BookResponse, RecyclerItemHomeBinding>(
                        layoutId = R.layout.recycler_item_home,
                        bindingVariableId = BR.response,
                        clickItemEvent = itemClickListener
                    ) {}
                addOnScrollListener(endLessScrollListener3(BOOK_ITEMNEWALL))
            }
        }
    }

    private fun endLessScrollListener1(queryType: String): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(binding.rvBookBestseller.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                homeViewModel.loadMore(page = page + 1, queryType = queryType)
            }
        }
    }

    private fun endLessScrollListener2(queryType: String): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(binding.rvBookNew.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                homeViewModel.loadMore(page = page + 1, queryType = queryType)
            }
        }
    }

    private fun endLessScrollListener3(queryType: String): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(binding.rvBookAll.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                homeViewModel.loadMore(page = page + 1, queryType = queryType)
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