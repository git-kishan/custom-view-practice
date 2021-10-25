package com.example.testapp2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.hypot

class CircleAnimatedView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val wavePaint : Paint
    private val waveGap : Float
    private var waveAnimator : ValueAnimator? = null
    private var waveRadiusOffset = 0f
    set(value) {
        field = value
        invalidate()
    }

    private var maxRadius = 0f
    private var center = PointF(0f,0f)
    private var initialRadius = 0f


    init {
        wavePaint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            setAlpha(1f)
            strokeWidth = 3f
            style= Paint.Style.STROKE
        }

        waveGap = 50f
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        waveAnimator = ValueAnimator.ofFloat(0f,waveGap).apply {
            addUpdateListener {
                waveRadiusOffset = it.animatedValue as Float
            }
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            duration = 1000
        }
        waveAnimator?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        waveAnimator?.cancel()
        waveAnimator?.removeAllListeners()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        center.set((w/2).toFloat(),(h/2).toFloat())
        maxRadius = hypot(center.x,center.y)
        initialRadius = w/waveGap
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var currentRadius = initialRadius + waveRadiusOffset
        while(currentRadius < maxRadius){
            canvas?.drawCircle(center.x,center.y,currentRadius,wavePaint)
            currentRadius+=waveGap
        }
    }

}