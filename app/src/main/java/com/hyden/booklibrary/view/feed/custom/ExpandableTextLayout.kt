package com.hyden.booklibrary.view.feed.custom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.util.ConstUtil.Companion.DEFAULT_COLLAPSEDLINES
import com.hyden.booklibrary.view.feed.custom.adapter.ExpandCollapseAnimationLayout
import com.hyden.booklibrary.view.feed.FeedViewModel
import com.hyden.booklibrary.view.feed.model.FeedData
import kotlin.math.max

class ExpandableTextLayout : LinearLayout, View.OnClickListener {

    private var collapsedLines = DEFAULT_COLLAPSEDLINES
    private var collapsedHeight = 0
    private var expanedHeight = 150

    private var expandableId = R.id.tv_expandable
    private var showMoreId = R.id.tv_show_more
    private var commentdId = R.id.tv_show_more
    private var expandId = R.id.ib_expand_collapse
    lateinit var animation: ExpandCollapseAnimationLayout

    private var indexFalse: Int = -1
    private var indexTrue: Int = -1
    private var index: Int = -1

    lateinit var feedVm: FeedViewModel

    //    private var tvContents: TextView? = null
//    private var ibToggle: ImageButton? = null
    private val ibToggle by lazy { this.findViewById<ImageButton>(R.id.ib_expand_collapse) }
    //    private val tvContents by lazy { this.findViewById<TextView>(R.id.tv_expandable) }
//    private val tvShowMore by lazy { this.findViewById<TextView>(R.id.tv_show_more) }
    private val tvContents by lazy { ExpandableTextView(context) }
    private val tvShowMore by lazy { TextView(context) }
    private val tvComments by lazy { TextView(context) }


    constructor(context: Context) : super(context) {}
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        initView(attributeSet)
    }

    private fun initView(attributeSet: AttributeSet) {
        val typeArrary =
            context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableTextLayout)

        collapsedLines = typeArrary.getInt(
            R.styleable.ExpandableTextLayout_maxCollapsedLinesText,
            DEFAULT_COLLAPSEDLINES
        )
        expandableId = typeArrary.getResourceId(
            R.styleable.ExpandableTextLayout_tvExpandableId,
            R.id.tv_expandable
        )
        commentdId = typeArrary.getResourceId(
            R.styleable.ExpandableTextLayout_tvCommentsId,
            R.id.tv_comments
        )
        expandId = typeArrary.getResourceId(
            R.styleable.ExpandableTextLayout_ibExpandCollapseToggleId,
            R.id.ib_expand_collapse
        )


        orientation = VERTICAL
        addView(initTvContents())
        addView(initTvShowMore())
        addView(initTvComments())
        typeArrary.recycle()

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViews()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun initTvContents() : TextView {
        tvContents.id = expandableId
        tvContents.textSize = 13f
        tvContents.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return tvContents
    }
    private fun initTvShowMore() : TextView {
        tvShowMore.id = showMoreId
        tvShowMore.text = "자세히보기"
        tvShowMore.textSize = 12f
        tvShowMore.setTextColor(Color.GRAY)
        tvShowMore.visibility = View.GONE
        return tvShowMore
    }
    private fun initTvComments() : TextView {
        val layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.topMargin = 40

        tvComments.id = commentdId
        tvComments.textSize = 12f
        tvComments.layoutParams = layoutParams
        tvComments.setTextColor(resources.getColor(R.color.colorAccent,null))
        tvComments.alpha = 0.7f
        tvComments.text = "답글 1개 보기"
        tvComments.visibility = View.GONE
        return tvComments
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_expandable -> {
                Toast.makeText(context, "내용", Toast.LENGTH_SHORT).show()
            }
            R.id.tv_show_more -> {
                showMore(feedVm.feedItems.value!![index].isExpanded)
                feedVm.feedItems.value!![index].isExpanded =
                    feedVm.feedItems.value!![index].isExpanded.not()
            }
            R.id.tv_comments -> {
                Toast.makeText(context, "댓글", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun findViews() {
        tvContents.maxLines = collapsedLines
        tvContents.setOnClickListener(this)
        tvShowMore.setOnClickListener(this)
        tvComments.setOnClickListener(this)
    }

    private fun showMore(isExpanded: Boolean) {
        when (isExpanded) {
            true -> {
                animation =
                    ExpandCollapseAnimationLayout(
                        this,
                        tvContents,
                        tvContents.height,
                        collapsedHeight
                    )
            }
            false -> {
                animation =
                    ExpandCollapseAnimationLayout(
                        this,
                        tvContents,
                        tvContents.height,
                        expanedHeight
                    )
            }
        }

        if (isExpanded && tvContents.lineCount > collapsedLines) {
            tvShowMore.text = "자세히보기"
            tvShowMore.visibility = View.VISIBLE
        } else {
            tvShowMore.text = "접기"
        }

        animation.duration = 500
        clearAnimation()
        startAnimation(animation)
    }

    fun setText(userName: String?, text: String?, feedVm: FeedViewModel, feedData: Feed?) {
        val bold = Typeface.createFromAsset(resources.assets, "scdream9_black.otf")
        val sb = SpannableStringBuilder("$userName $text").apply {
            //        setSpan(StyleSpan(Typeface.BOLD),0, userName?.length ?: 0, SPAN_EXCLUSIVE_EXCLUSIVE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                setSpan(TypefaceSpan(bold),0,userName?.length ?: 0 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                //TODO 텍스트 폰트 적용
                setSpan(bold,0,userName?.length ?: 0 , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        tvContents.text = sb

        postDelayed({
            this.feedVm = feedVm
            indexFalse = feedVm.feedItems.value?.indexOf(FeedData(feedData, false))!!
            indexTrue = feedVm.feedItems.value?.indexOf(FeedData(feedData, true))!!
            index = max(indexFalse, indexTrue)

            if (index != -1) {
                feedVm.feedItems.value?.let {
                    setContentHeight(!it[index].isExpanded)
                }
            }
        }, 100)
    }

    private fun setContentHeight(isExpanded: Boolean) {
        expanedHeight = tvContents.lineHeight * tvContents.lineCount
        collapsedHeight = if (tvContents.lineCount > 3) {
            tvContents.lineHeight * collapsedLines
        } else {
            tvContents.lineHeight * tvContents.lineCount
        }

        when (isExpanded) {
            true -> {
                tvContents.height = collapsedHeight
                tvContents.layoutParams.height = collapsedHeight
            }
            false -> {
                tvContents.height = expanedHeight
                tvContents.layoutParams.height = expanedHeight
            }
        }

        if (isExpanded && tvContents.lineCount > collapsedLines) {
            tvShowMore.visibility = View.VISIBLE
            tvShowMore.text = "자세히보기"
        } else if(!isExpanded) {
            tvShowMore.visibility = View.VISIBLE
            tvShowMore.text = "접기"
        } else {
            tvShowMore.visibility = View.GONE
        }
    }
}