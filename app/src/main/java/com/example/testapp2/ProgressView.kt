package com.example.testapp2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class ProgressView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val ovalSpace = RectF()
    private val parentArcColor = Color.parseColor("#BCD2EE")
    private val fillArcColor = Color.parseColor("#2B4F81")
    private var currentPercentage = 0
    private val ARC_FULL_ROTATION_DEGREE = 360F
    private val PERCENTAGE_DIVIDER = 100F

    private fun parentArcPaint() : Paint{
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.color = parentArcColor
        paint.strokeWidth = 40F
        return paint
    }

    private fun fillArcPaint () : Paint{
        val paint = Paint().apply {
            this.isAntiAlias = true
            this.style = Paint.Style.STROKE
            this.color =fillArcColor
            this.strokeWidth = 40F
            this.strokeCap = Paint.Cap.ROUND
        }
        return paint
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setSpace()
        canvas?.let {
            drawBackgroundArc(it)
            drawInnerArc(it)
        }
    }


    private fun drawInnerArc(canvas : Canvas){
        canvas.drawArc(ovalSpace,270f,getCurrentPercentageToFill(),false,fillArcPaint())
    }

    private fun drawBackgroundArc(canvas : Canvas){
        canvas.drawArc(ovalSpace,0f,360F,false,parentArcPaint())
    }

    private fun getCurrentPercentageToFill() =
        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER))

    private fun setSpace(){
        val horizontalCenter = width.div(2).toFloat()
        val verticalCenter = height.div(2).toFloat()
        val ovalSize = 200
        ovalSpace.set(
            horizontalCenter-ovalSize,
            verticalCenter-ovalSize,
            horizontalCenter+ovalSize,
            verticalCenter+ovalSize
        )
    }

    fun animateProgress(progress : Int){
        val animator = ValueAnimator.ofFloat(currentPercentage.toFloat(),progress.toFloat()).apply{
            duration = 200
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                val percentage = it.animatedValue as Float
                currentPercentage = percentage.toInt()
                invalidate()
            }
        }.start()



    }
}