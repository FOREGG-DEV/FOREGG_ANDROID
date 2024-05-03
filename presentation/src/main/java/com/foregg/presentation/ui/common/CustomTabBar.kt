package com.foregg.presentation.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.foregg.presentation.R
import com.foregg.presentation.databinding.CustomTabBarBinding


class CustomTabBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CustomTabBarBinding
    private val tabCount: Int
    private val leftTabText: String?
    private val middleTabText: String?
    private val rightTabText: String?

    companion object{
        const val TWO_TAB = 2
    }

    val leftTab : AppCompatButton
        get() = binding.btnLeft

    val middleTab : AppCompatButton
        get() = binding.btnMiddle

    val rightTab : AppCompatButton
        get() = binding.btnRight

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_tab_bar, this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_tab_bar, defStyleAttr, 0)

        tabCount = typedArray.getInteger(R.styleable.custom_tab_bar_tabCount, TWO_TAB)
        leftTabText = typedArray.getString(R.styleable.custom_tab_bar_leftText)
        middleTabText = typedArray.getString(R.styleable.custom_tab_bar_middleText)
        rightTabText = typedArray.getString(R.styleable.custom_tab_bar_rightText)
        if(tabCount == TWO_TAB){
            middleTab.visibility = View.GONE
        }

        binding.apply {
            leftTab.text = leftTabText
            if(tabCount != TWO_TAB) middleTab.text = middleTabText
            rightTab.text = rightTabText
        }
        typedArray.recycle()
    }

    fun leftBtnClicked(){
        leftTab.setBackgroundResource(R.drawable.bg_rectangle_filled_main_radius_8)
        middleTab.setBackgroundColor(R.drawable.bg_rectangle_filled_white_radius_8)
        rightTab.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        leftTab.setTextColor(ContextCompat.getColor(context, R.color.white))
        middleTab.setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        rightTab.setTextColor(ContextCompat.getColor(context, R.color.gs_50))
    }

    fun middleBtnClicked(){
        middleTab.setBackgroundResource(R.drawable.bg_rectangle_filled_main_radius_8)
        leftTab.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        rightTab.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        leftTab.setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        middleTab.setTextColor(ContextCompat.getColor(context, R.color.white))
        rightTab.setTextColor(ContextCompat.getColor(context, R.color.gs_50))
    }

    fun rightBtnClicked(){
        rightTab.setBackgroundResource(R.drawable.bg_rectangle_filled_main_radius_8)
        middleTab.setBackgroundColor(R.drawable.bg_rectangle_filled_white_radius_8)
        leftTab.setBackgroundResource(R.drawable.bg_rectangle_filled_white_radius_8)
        leftTab.setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        middleTab.setTextColor(ContextCompat.getColor(context, R.color.gs_50))
        rightTab.setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}