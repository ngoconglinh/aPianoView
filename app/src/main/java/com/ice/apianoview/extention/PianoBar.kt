package com.ice.apianoview.extention

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.ice.apianoview.R
import com.ice.apianoview.listener.TouchListener

class PianoBar@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.black)
        style = Paint.Style.FILL
        alpha = 100
    }

    var highLightWidth = 10f

    var progress = 50
        set(value) {
            field = value
            invalidate()
        }

    var attackPianoWidth = 100
        set(value) {
            field = value
            highLightWidth = (attackWidth.toFloat() / value.toFloat()) * width.toFloat()
            invalidate()
        }

    var attackWidth = 20
        set(value) {
            field = value
            highLightWidth = (value.toFloat() / attackPianoWidth.toFloat()) * width.toFloat()
            invalidate()
        }

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.PianoBar, defStyleAttr, 0)

        val image = a.getResourceId(R.styleable.PianoBar_imageBar, R.drawable.piano_bar)
        val mP = a.getInt(R.styleable.PianoBar_progress, 0)
        setImageResource(image)
        scaleType = ScaleType.FIT_XY
        progress = if (mP > 100) 100 else mP
        a.recycle()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val mX = (((width.toFloat() - highLightWidth) / 100f) * progress).toInt()

        val rectDarkLeft = Rect(0, 0, mX, height)
        canvas.drawRect(rectDarkLeft, paint)

        val rectDarkRight = Rect(mX + highLightWidth.toInt(), 0, width, height)
        canvas.drawRect(rectDarkRight, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchListener?.onTouchDown()
                onTouch(event.x)
            }
            MotionEvent.ACTION_MOVE -> {
                onTouch(event.x)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchListener?.onTouchUp()
                if (progress > 100) progress = 100
            }
        }
        return true
    }

    private fun onTouch(touchX: Float) {
        val perHighLightWidth = highLightWidth / 2

        val limitRight = width - perHighLightWidth
        val mX = touchX.coerceIn(perHighLightWidth, limitRight)
        progress = (((mX - perHighLightWidth) / (limitRight -  perHighLightWidth)) * 100).toInt()
        progressListener?.onUserChangedProgress(progress)
    }

    fun addListener(progressListener: ProgressListener) {
        this.progressListener = progressListener
    }

    fun addTouchListener(touchListener: TouchListener) {
        this.touchListener = touchListener
    }

    private var touchListener: TouchListener? = null

    private var progressListener: ProgressListener? = null

    interface ProgressListener {
        fun onUserChangedProgress(progress: Int)
    }
}