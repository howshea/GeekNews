package com.howshea.basemodule.component.viewGroup

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
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
 * @attr R.styleable.SimpleToolbar_navIcon
 * @attr R.styleable.SimpleToolbar_menuIcon
 * @attr R.styleable.SimpleToolbar_contentHeight
 * @attr R.styleable.SimpleToolbar_title
 * @attr R.styleable.SimpleToolbar_titleStyle
 * @attr R.styleable.SimpleToolbar_titleSize
 * @attr R.styleable.SimpleToolbar_titleColor
 */

class SimpleToolbar : FrameLayout {
    //icon
    private var navButton: ImageButton? = null
    private var navigationDrawable: Drawable? = null
    private var menuButton: ImageButton? = null
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
    //标题
    var title: CharSequence = ""
        set(value) {
            field = value
            titleTextView
                ?.let {
                    it.layoutParams = getTitleLp()
                    it.text = title
                }
                ?: ensureTitleView()
        }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context
            ?.obtainStyledAttributes(attrs, R.styleable.SimpleToolbar)
            ?.apply {
                if (hasValue(R.styleable.SimpleToolbar_navIcon))
                    navigationDrawable = getDrawable(R.styleable.SimpleToolbar_navIcon)

                if (hasValue(R.styleable.SimpleToolbar_menuIcon))
                    menuDrawable = getDrawable(R.styleable.SimpleToolbar_menuIcon)

                if (hasValue(R.styleable.SimpleToolbar_contentHeight)) {
                    contentHeight = getDimension(R.styleable.SimpleToolbar_contentHeight, 0f).toInt()
                    (contentHeight < iconSize) {
                        //contentHeight如果被设置小于icon的高度，就抛出异常
                        throw IllegalArgumentException("contentHeight must be greater than iconSize")
                    }
                }
                title = getString(R.styleable.SimpleToolbar_title) ?: ""
                titleStyle = getInt(R.styleable.SimpleToolbar_titleStyle, 1)
                titleColor = getColor(R.styleable.SimpleToolbar_titleColor, titleColor)
                titleSize = getDimension(R.styleable.SimpleToolbar_titleSize, titleSize)
                recycle()
            }
        setBackgroundColor(Color.parseColor("#ffffff"))
        initSubView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

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


    private fun ensureTitleView() {
        titleTextView ?: let {
            titleTextView = AppCompatTextView(context, null).apply {
                text = title
                setTextColor(titleColor)
                setTypeface(Typeface.DEFAULT, titleStyle)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
                setSingleLine()
                ellipsize  = TextUtils.TruncateAt.END
                gravity = Gravity.CENTER
                layoutParams = getTitleLp()
            }.apply {
                addSystemView()
            }
        }
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
            marginStart = iconSize + dp(32)
            marginEnd = iconSize + dp(32)
            gravity = Gravity.CENTER
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
