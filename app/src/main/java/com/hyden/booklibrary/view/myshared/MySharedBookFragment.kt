package com.hyden.booklibrary.view.myshared

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.hyden.base.BaseFragment
import com.hyden.base.BaseItemsApdater
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.FragmentMySharedBinding
import com.hyden.booklibrary.view.detail.mysaved.SavedDetailActivity
import com.hyden.ext.moveToActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MySharedBookFragment : BaseFragment<FragmentMySharedBinding>(R.layout.fragment_my_shared) {

    private val viewModel by viewModel<MySharedBookViewMiodel>()
    private val adapter by lazy {
        BaseItemsApdater(R.layout.recycler_item_library, BR.response,viewModel.clickListener)
    }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = this@MySharedBookFragment.viewModel
            adapter = this@MySharedBookFragment.adapter
        }
        viewModel.loadBook()
    }

    override fun initBind() {
        viewModel.singleLiveEvent.observe(this@MySharedBookFragment, Observer {
            Intent(context,SavedDetailActivity::class.java).run {
                putExtra(getString(R.string.book_info),(it as BookEntity))
                moveToActivity(this)
            }
        })
    }

    companion object {
        fun newInstance() = MySharedBookFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}