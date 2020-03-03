package com.howshea.basemodule.component.viewGroup

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.howshea.basemodule.R
import com.howshea.basemodule.extentions.invoke
import com.howshea.basemodule.extentions.yes
import com.howshea.basemodule.utils.dp
import com.howshea.basemodule.utils.sp


/**
 * Created by Howshea
 * on 2018/7/16.
 */
/**
 * SToolbar means simplified toolbar
 *
 * @attr R.styleable.SToolbar_navIcon
 * @attr R.styleable.SToolbar_menuIcon
 * @attr R.styleable.SToolbar_contentHeight
 * @attr R.styleable.SToolbar_title
 * @attr R.styleable.SToolbar_titleStyle
 * @attr R.styleable.SToolbar_titleSize
 * @attr R.styleable.SToolbar_titleColor
 */

class SToolbar : FrameLayout {
    //icon
    private var navButton: AppCompatImageButton? = null
    private var navigationDrawable: Drawable? = null
    private var menuButton: AppCompatImageButton? = null
    private var menuDrawable: Drawable? = null
    //title
    private var titleTextView: TextView? = null
    private var titleStyle = 1
    //默认字体颜色
    @ColorInt
    private var titleColor = Color.parseColor("#707070")
    //默认字体大小
    private var titleSize = sp(20).toFloat()
    //默认高度48dp
    private var contentHeight = dp(48)
    //默认icon宽高
    private val iconSize = dp(24)
    private val iconTopBottomMargin
        get() = (contentHeight - iconSize) / 2
    //title默认居中
    private var titleGravity = 1
    //title margin
    private var titleMarginStart: Int? = null
    private var titleMarginEnd: Int? = null
    //标题
    var title: CharSequence = "                          "
        set(value) {
            field = value
            titleTextView
                ?.let {
                    it.text = value
                }
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        context.obtainStyledAttributes(attrs, R.styleable.SToolbar).apply {
            if (hasValue(R.styleable.SToolbar_navIcon))
                navigationDrawable = getDrawable(R.styleable.SToolbar_navIcon)

            if (hasValue(R.styleable.SToolbar_menuIcon))
                menuDrawable = getDrawable(R.styleable.SToolbar_menuIcon)

            if (hasValue(R.styleable.SToolbar_contentHeight)) {
                contentHeight = getDimension(R.styleable.SToolbar_contentHeight, 0f).toInt()
                (contentHeight < iconSize) {
                    //contentHeight如果被设置小于icon的高度，就抛出异常
                    throw IllegalArgumentException("contentHeight must be greater than iconSize")
                }
            }
            if (hasValue(R.styleable.SToolbar_titleMarginStart))
                titleMarginStart = getDimension(R.styleable.SToolbar_titleMarginStart, 0f).toInt()

            if (hasValue(R.styleable.SToolbar_titleMarginEnd))
                titleMarginEnd = getDimension(R.styleable.SToolbar_titleMarginEnd, 0f).toInt()

            title = getString(R.styleable.SToolbar_title) ?: title
            titleColor = getColor(R.styleable.SToolbar_titleColor, Color.parseColor("#707070"))
            titleSize = getDimension(R.styleable.SToolbar_titleSize, sp(20).toFloat())
            titleStyle = getInt(R.styleable.SToolbar_titleStyle, 1)
            titleGravity = getInt(R.styleable.SToolbar_titleGravity, 1)
            recycle()
        }
        initSubView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun initSubView() {
        ensureNavButtonView()
        ensureMenuButtonView()
        ensureTitleView()
    }

    private fun View.addSystemView() {
        val vlp = this.layoutParams
        val lp: LayoutParams
        lp = if (vlp == null) {
            generateDefaultLayoutParams()
        } else if (!checkLayoutParams(vlp)) {
            generateLayoutParams(vlp) as LayoutParams
        } else {
            vlp as LayoutParams
        }
        addView(this, lp)
    }

    private fun ensureNavButtonView() {
        navButton ?: let { navigationDrawable != null }
            .yes {
                navButton = AppCompatImageButton(context, null,
                    R.attr.toolbarNavigationButtonStyle).apply {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setImageDrawable(navigationDrawable)
                    layoutParams = navButtonLp
                }.apply {
                    addSystemView()
                }
            }
    }

    private fun ensureMenuButtonView() {
        menuButton ?: let { menuDrawable != null }
            .yes {
                menuButton = AppCompatImageButton(context, null,
                    R.attr.toolbarNavigationButtonStyle).apply {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setImageDrawable(menuDrawable)
                    layoutParams = menuButtonLp
                }.apply {
                    addSystemView()
                }
            }
    }


    @SuppressLint("WrongConstant")
    private fun ensureTitleView() {
        titleTextView ?: let {
            titleTextView = AppCompatTextView(context, null).apply {
                text = title
                includeFontPadding = false
                setTextColor(titleColor)
                setTypeface(Typeface.DEFAULT, titleStyle)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
                ellipsize = TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                gravity = Gravity.CENTER
                setSingleLine()
                layoutParams = getTitleLp()
            }.apply {
                addSystemView()
            }
        }
        titleTextView?.isSelected = true
    }

    private val navButtonLp: FrameLayout.LayoutParams by lazy {
        generateDefaultLayoutParams().apply {
            height = iconSize
            width = iconSize
            topMargin = iconTopBottomMargin
            bottomMargin = iconTopBottomMargin
            marginStart = dp(16)
            gravity = Gravity.START
        }
    }

    private val menuButtonLp: FrameLayout.LayoutParams by lazy {
        generateDefaultLayoutParams().apply {
            height = iconSize
            width = iconSize
            topMargin = iconTopBottomMargin
            bottomMargin = iconTopBottomMargin
            marginEnd = dp(16)
            gravity = Gravity.END
        }
    }

    private fun getTitleLp(): FrameLayout.LayoutParams {
        return generateDefaultLayoutParams().apply {
            marginStart = navButton?.let { iconSize + dp(32) } ?: dp(16)
            marginEnd = menuButton?.let { iconSize + dp(32) } ?: dp(16)
            titleMarginStart?.let {
                marginStart = it
            }
            titleMarginEnd?.let {
                marginEnd = it
            }
            height = contentHeight
            gravity = if (titleGravity == 1) Gravity.CENTER else Gravity.START or Gravity.CENTER_VERTICAL
        }
    }

    fun setOnNavClick(click: (v: View) -> Unit) {
        navButton?.setOnClickListener(click)
    }

    fun setOnMenuClick(click: (v: View) -> Unit) {
        menuButton?.setOnClickListener(click)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }
}

