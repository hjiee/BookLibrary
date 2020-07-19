package com.hyden.booklibrary.view.feed.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.util.ConstUtil.Companion.DEFAULT_COLLAPSEDLINES
import com.hyden.booklibrary.view.feed.custom.adapter.ExpandCollapseAnimation

class ExpandableTextView : TextView {


    private var collapsedLines = DEFAULT_COLLAPSEDLINES
    private var isExpanded = false
    private var collapsedHeight = 0
    private var expanedHeight = 0

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
        val typeArrary = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableTextView)

        collapsedLines = typeArrary.getInt(
            R.styleable.ExpandableTextView_maxCollapsedLines,
            DEFAULT_COLLAPSEDLINES
        )



//        setOnClickListener(this)
        typeArrary.recycle()
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        findViews()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        expanedHeight = lineHeight * lineCount + 10
//        collapsedHeight = lineHeight * collapsedLines + 10

    }

//    override fun onClick(view: View?) {
//
//        var animation: ExpandCollapseAnimation
//        when (isExpanded) {
//            true -> {
//                animation =
//                    ExpandCollapseAnimation(
//                        this,
//                        height,
//                        collapsedHeight
//                    )
////                maxLines = collapsedLines
//            }
//            false -> {
//                animation =
//                    ExpandCollapseAnimation(
//                        this,
//                        height,
//                        expanedHeight
//                    )
////                maxLines = lineCount
//            }
//        }
//        isExpanded = isExpanded.not()
//        animation.duration = 300
//        clearAnimation()
//        startAnimation(animation)
//    }

    private fun findViews() {
        maxLines = collapsedLines
    }
}