package com.example.views.ui.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import kotlin.math.ceil

object AndroidUtils {
    fun dp(context: Context, dp: Int): Int =
        ceil(context.resources.displayMetrics.density * dp).toInt()
    fun sp(context: Context, dp: Float) :Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, context.resources.displayMetrics).toInt()

}