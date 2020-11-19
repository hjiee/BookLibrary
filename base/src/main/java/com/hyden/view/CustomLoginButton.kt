package com.hyden.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.hyden.base.R

class CustomLoginButton : ConstraintLayout {
    private var llBackground: ConstraintLayout? = null
    private var ivSymbol: ImageView? = null
    private var tvText: TextView? = null

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initView()
        getAttrs(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs) {
        initView()
        getAttrs(attrs, defStyle)
    }

    fun initView() {
        val strInflater = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(strInflater) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.custom_login_button, this, false)
        addView(view)
        llBackground = findViewById(R.id.layout_login_background) // 버튼 배경
        ivSymbol = findViewById(R.id.iv_logo) // 버튼내부 로고 이미지
        tvText = findViewById(R.id.tv_login_with_name) // 버튼이름
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginButton)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginButton, defStyle, 0)
        setTypeArray(typedArray)
    }

    /**
     * default background color : white
     * default text color : black
     * default text size : 12
     */
    private fun setTypeArray(typedArray: TypedArray) {
        val backgroundResId = typedArray.getResourceId(R.styleable.LoginButton_bg, R.drawable.border_background)
        val backgroundColorId = typedArray.getColor(R.styleable.LoginButton_bgColor,resources.getColor(R.color.colorWhite,null))
        val symbolResId = typedArray.getResourceId(R.styleable.LoginButton_symbol, R.drawable.border_background)
        val color = typedArray.getColor(R.styleable.LoginButton_textColor, resources.getColor(R.color.colorBlack,null))
        val string = typedArray.getString(R.styleable.LoginButton_text)
        val size = typedArray.getFloat(R.styleable.LoginButton_textSize,12f)

        llBackground?.apply {
            setBackgroundResource(backgroundResId)
            (background as? LayerDrawable)?.setTint(backgroundColorId)
        }

        ivSymbol?.apply {
            setImageResource(symbolResId)
        }

        tvText?.apply {
            setTextColor(color)
            textSize = size
            text = string
        }

        typedArray.recycle()
    }
}