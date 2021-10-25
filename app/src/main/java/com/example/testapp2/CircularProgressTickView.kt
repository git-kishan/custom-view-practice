package com.example.testapp2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd

class CircularProgressTickView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var paint : Paint? = null
    private var viewWidthCenter : Float = 0f
    private var viewHeightCenter : Float = 0f
    private var rectF : RectF? = null
    private var circleRadius = 200f
    private var sweepAngle = 0f

    private var valueAnimator : ValueAnimator? = null
    private var tickValueAnimator : ValueAnimator? = null

    private var drawingPath : Path? = null
    private var path : Path? = null
    private var pathMeasure : PathMeasure? = null

    private var pathLength = 0f
    private var position = FloatArray(2)


    private var startX = viewWidthCenter - 120
    private var startY = viewHeightCenter -20

    private var middleX = viewWidthCenter-40
    private var middleY = viewHeightCenter+80

    private var finalX = viewWidthCenter + 120
    private var finalY = viewHeightCenter - 90


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidthCenter = (w/2).toFloat()
        viewHeightCenter = (h/2).toFloat()
        init()
    }

    private fun init(){
        path = Path()
        drawingPath = Path()

        startX = viewWidthCenter - 120
        startY = viewHeightCenter -20

        middleX = viewWidthCenter-40
        middleY = viewHeightCenter+80

        finalX = viewWidthCenter + 120
        finalY = viewHeightCenter - 90

        path?.apply {
            moveTo(startX, startY)
            lineTo(middleX,middleY)
            lineTo(finalX,finalY)
        }

        drawingPath?.moveTo(startX,startY)
        pathMeasure = PathMeasure(path,false)
        pathLength = pathMeasure?.length ?: 0f

        paint = Paint().apply {
            color = Color.parseColor("#0f67f5")
            setAlpha(1f)
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            strokeWidth = 20f
        }

        rectF = RectF(
            viewWidthCenter - circleRadius,
            viewHeightCenter - circleRadius,
            viewWidthCenter + circleRadius,
            viewHeightCenter + circleRadius
        )

        valueAnimator = ValueAnimator.ofFloat(0f,360f).apply {
            addUpdateListener {
                sweepAngle = it.animatedValue as Float
                postInvalidate()
            }
            doOnEnd {
                tickValueAnimator?.start()
            }
            interpolator = AccelerateInterpolator()
            duration = 1500
        }

        tickValueAnimator = ValueAnimator.ofFloat(0f,1f).apply {
            addUpdateListener {
                val pathDistance = it.animatedValue as Float
                val distance = pathDistance * pathLength
                pathMeasure?.getPosTan(distance,position,null)
                drawingPath?.lineTo(position[0],position[1])
                postInvalidate()
            }
            interpolator = AccelerateDecelerateInterpolator()
            duration = 1200
        }
    }


     fun start(){
         drawingPath?.reset()
         drawingPath?.moveTo(startX,startY)
         valueAnimator?.start()
    }
     fun stop(){
         drawingPath?.reset()
         drawingPath?.moveTo(startX,startY)
         valueAnimator?.cancel()
         tickValueAnimator?.cancel()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(rectF!!,0f,sweepAngle,false,paint!!)
        canvas?.drawPath(drawingPath!!, paint!!)
    }

}