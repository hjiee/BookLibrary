package com.hyden.booklibrary.view.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.hyojin.util.EndlessRecyclerViewScrollListener
import com.google.android.gms.ads.AdRequest
import com.hyden.base.BaseFragment
import com.hyden.base.BaseItemsApdater
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.databinding.FragmentHomeBinding
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BESTSELLER
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BLOGBEST
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEW_SPECIAL
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEW_ALL
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASELIMIT
import com.hyden.booklibrary.view.detail.UnSavedDetailActivity
import com.hyden.ext.loadUrl
import com.hyden.ext.moveToActivity
import com.hyden.ext.numberFormatter
import com.hyden.util.ImageTransformType
import com.hyden.util.ItemClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel by viewModel<HomeViewModel>()
    private val timeInterval = 10000L
    private val imageType = ImageTransformType.ROUND

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when (item) {
                    is BookItem -> {
                        Intent(activity, UnSavedDetailActivity::class.java).apply {
                            putExtra(getString(R.string.book_info), item)
                            moveToActivity(this)
                        }
                    }
                }
            }
        }
    }

    private val handler = Handler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.loadBook(page = Random.nextInt(1, 4), queryType = BOOK_BLOGBEST)
        homeViewModel.loadBook(queryType = BOOK_BESTSELLER)
        homeViewModel.loadBook(queryType = BOOK_ITEMNEW_SPECIAL)
        homeViewModel.loadBook(queryType = BOOK_ITEMNEW_ALL)
        observing()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable())
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initBind() {
        binding.apply {
            vm = homeViewModel
            includeAdBanner.adView.loadAd(AdRequest.Builder().build())
            rvBookBestseller.apply {
                adapter = BaseItemsApdater(R.layout.recycler_item_home, BR.book, itemClickListener)
                addOnScrollListener(endLessScrollListener(BOOK_BESTSELLER, layoutManager))
            }
            rvBookNew.apply {
                adapter = BaseItemsApdater(R.layout.recycler_item_home, BR.book, itemClickListener)
                addOnScrollListener(endLessScrollListener(BOOK_ITEMNEW_SPECIAL, layoutManager))
            }
            rvBookAll.apply {
                adapter = BaseItemsApdater(R.layout.recycler_item_home, BR.book, itemClickListener)
                addOnScrollListener(endLessScrollListener(BOOK_ITEMNEW_ALL, layoutManager))
            }
        }
    }

    override fun observing() {
        super.observing()
        binding.run {
            homeViewModel.bookBlogBest.observe(this@HomeFragment, Observer {
                val initRand = Random.nextInt(DATABASELIMIT)
                ivBookCoverTemp.loadUrl(it[initRand].cover, imageType,resources.getInteger(R.integer.book_image_radius))
                ivBookCover.loadUrl(it[initRand].cover, imageType,resources.getInteger(R.integer.book_image_radius))
                tvTitle.text = homeViewModel.bookBlogBest.value!![initRand].title
                tvPrice.text = homeViewModel.bookBlogBest.value!![initRand].priceSales?.numberFormatter() + "원"

                Observable.interval(timeInterval, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val rand = Random.nextInt(DATABASELIMIT)
                        handler.postDelayed({ runnable(rand) }, 3000)

                        ivBookCover.loadUrl(homeViewModel.bookBlogBest.value!![rand].cover, imageType,resources.getInteger(R.integer.book_image_radius))
                        tvTitle.text = homeViewModel.bookBlogBest.value!![rand].title
                        tvPrice.text = homeViewModel.bookBlogBest.value!![rand].priceSales?.numberFormatter() + "원"
                    }.addTo(compositeDisposable)
            })
        }
    }

    private fun endLessScrollListener(
        queryType: String,
        layoutManager: RecyclerView.LayoutManager?
    ): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                homeViewModel.loadMore(page = page + 1, queryType = queryType)
            }
        }
    }

    private fun runnable(rand: Int = 0) = Runnable {
        binding.run {
            ivBookCoverTemp.loadUrl(homeViewModel.bookBlogBest.value!![rand].cover, imageType,resources.getInteger(R.integer.book_image_radius))
            tvTitle.text = homeViewModel.bookBlogBest.value!![rand].title
            tvPrice.text =
                homeViewModel.bookBlogBest.value!![rand].priceSales?.numberFormatter() + "원"
        }
    }


    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}