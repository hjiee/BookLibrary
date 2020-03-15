package com.hyden.booklibrary.util.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.opengl.Visibility
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import com.hyden.booklibrary.R
import com.hyden.booklibrary.util.ConstUtil.Companion.DEFAULT_COLLAPSEDLINES
import com.hyden.booklibrary.util.ExpandCollapseAnimationLayout
import com.hyden.util.LogUtil
import com.hyden.util.LogUtil.LogW

class ExpandableTextLayout : LinearLayout, View.OnClickListener {

    private var collapsedLines = DEFAULT_COLLAPSEDLINES
    private var isExpanded = false
    private var collapsedHeight = 0
    private var expanedHeight = 0

    private var tvExPandableId = R.id.tv_expandable
    private var tvShowMoreId = R.id.tv_show_more
    private var ibExpandId = R.id.ib_expand_collapse

    //    private var tvContents: TextView? = null
//    private var ibToggle: ImageButton? = null
    private val ibToggle by lazy { this.findViewById<ImageButton>(R.id.ib_expand_collapse) }
//    private val tvContents by lazy { this.findViewById<TextView>(R.id.tv_expandable) }
//    private val tvShowMore by lazy { this.findViewById<TextView>(R.id.tv_show_more) }
    private val tvContents by lazy { ExpandableTextView(context) }
    private val tvShowMore by lazy { TextView(context) }


    constructor(context: Context) : super(context) {}
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        init(attributeSet)
    }

    private fun init(attributeSet: AttributeSet) {
        val typeArrary =
            context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableTextLayout)

        collapsedLines = typeArrary.getInt(
            R.styleable.ExpandableTextLayout_maxCollapsedLinesText,
            DEFAULT_COLLAPSEDLINES
        )
        tvExPandableId = typeArrary.getResourceId(
            R.styleable.ExpandableTextLayout_tvExpandableId,
            R.id.tv_expandable
        )
        ibExpandId = typeArrary.getResourceId(
            R.styleable.ExpandableTextLayout_ibExpandCollapseToggleId,
            R.id.ib_expand_collapse
        )

        // enforces vertical orientation
        orientation = VERTICAL
        tvContents.id = tvExPandableId
        tvContents.layoutParams = LayoutParams(MATCH_PARENT,WRAP_CONTENT)
        addView(tvContents)

        tvShowMore.id = tvShowMoreId
        tvShowMore.layoutParams = LayoutParams(MATCH_PARENT,WRAP_CONTENT)
        tvShowMore.text = "자세히보기"
        tvShowMore.visibility = View.GONE
        tvShowMore.setTextColor(Color.GRAY)
        addView(tvShowMore)
        typeArrary.recycle()

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViews()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        expanedHeight = tvContents.lineHeight * tvContents.lineCount + 10
        collapsedHeight = tvContents.lineHeight * collapsedLines + 10
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tv_expandable -> {
                Toast.makeText(context, "ㅆ", Toast.LENGTH_SHORT).show()
            }
            R.id.tv_show_more -> {
                showMore()
            }
        }

    }
    private fun showMore() {
        var animation: ExpandCollapseAnimationLayout

        when (isExpanded) {
            true -> {
                animation = ExpandCollapseAnimationLayout(
                    this,
                    tvContents,
                    tvContents.height,
                    collapsedHeight
                )
            }
            false -> {
                animation = ExpandCollapseAnimationLayout(
                    this,
                    tvContents,
                    tvContents.height,
                    expanedHeight
                )
            }
        }
        tvShowMore.visibility = View.INVISIBLE
        isExpanded = isExpanded.not()
        animation.duration = 500
        clearAnimation()
        startAnimation(animation)
    }
    private fun findViews() {
        tvContents.maxLines = collapsedLines
        tvContents.setOnClickListener(this)
        tvShowMore.setOnClickListener(this)
    }

    fun setText(text : String) {
        val sb = SpannableStringBuilder().append(text)
        sb.setSpan(StyleSpan(Typeface.BOLD),0,text.split(" ")[0].count(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        sb.setSpan(ForegroundColorSpan(Color.GRAY),0,text.split(" ")[0].count(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvContents.text = sb
//        LogW("text : ${tvContents.text}")
//        LogW("height : ${tvContents.height}")
//        LogW("lineCount : ${tvContents.lineCount}")
//        LogW("DEFAULT_COLLAPSEDLINES : ${DEFAULT_COLLAPSEDLINES}")
//        LogW("collapsedLines : ${collapsedLines}")
//        LogW("---------------------------------------")

        if(!isExpanded && tvContents.lineCount > collapsedLines) {
            tvShowMore.visibility = View.VISIBLE
        } else {
            tvShowMore.visibility = View.INVISIBLE
        }
    }
}