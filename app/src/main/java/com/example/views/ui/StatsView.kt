package com.example.views.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import com.example.views.R
import com.example.views.ui.utils.AndroidUtils
import java.lang.Math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes,
){
    private var radius = 0F
    private var center = PointF(0F, 0F)
    private var oval = RectF(0F, 0F, 0F, 0F)

//    private var oval = RectF(
//        center.x - radius,
//        center.y - radius,
//        center.x + radius,
//        center.y + radius
//    )

    private var fontSize = AndroidUtils.sp(context, 40F).toFloat()
    private var lineWidth = AndroidUtils.dp(context, 5).toFloat()
    private var colors = emptyList<Int>()

    private var progress = 0F
    private var valueAnimator: ValueAnimator? = null
    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView){
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth)
            fontSize = getDimension(R.styleable.StatsView_fontSize, fontSize)
            val resId = getResourceId(R.styleable.StatsView_colors, 0)
            colors = resources.getIntArray(resId).toList()}
    }


    private val paint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        strokeWidth = lineWidth
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }
    private val textPaint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        textSize = this@StatsView.fontSize
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }
    var data: List<Float> = emptyList()
        set(value) {
            field = value
            update()
        }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w,h)/2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius, center.y - radius,
            center.x + radius, center.y + radius,
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()){
            canvas.drawText(
                "%.2f%%".format(0),
                center.x,
                center.y + textPaint.textSize / 4,
                textPaint,
            )
            return
        }

        val progressAngle = progress * 360F
        var startAngle = -90F
         for ((index, datum) in data.withIndex()) {
            val angle = 360F * datum / data.sum()
            paint.color = colors.getOrNull(index) ?: generateRandomColor()

            canvas.drawArc(oval, startAngle + progressAngle, angle, false, paint)
            startAngle += angle
        }


        canvas.drawText(
            "%.2f%%".format(data.sum()  * progress),
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint,
        )
        return
    }
    private fun update() {
        valueAnimator?.let {
            it.removeAllListeners()
            it.cancel()
        }
        progress = 0F

        valueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
            addUpdateListener { anim ->
                progress = anim.animatedValue as Float
                invalidate()
            }
            duration = 5_000
            interpolator = LinearInterpolator()
        }.also {
            it.start()
        }
    }


    private fun generateRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}