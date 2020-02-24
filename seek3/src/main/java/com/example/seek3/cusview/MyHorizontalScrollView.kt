package com.example.seek3.cusview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller

/**
 * Created by hezhanghe on 2020-02-24.
 * github: https://github.com/HadesHe
 */
class MyHorizontalScrollView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var mLastY: Float = 0f
    private var mLastX: Float = 0f
    private var mChildIndex: Int = 0
    private var mChildWidth: Int = 0
    private var mChildrenSize: Int = 0
    private var mVelocityTracker: VelocityTracker? = null
    private var mScroller: Scroller? = null

    init {
        if (mScroller == null) {
            mScroller = Scroller(getContext())
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measureWidth = 0
        var measureHeight = 0
        val childCount = childCount
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec)
        var widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec)
        var heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec)

        var defaultHeight = 100
        var defaultWidth = 100

        if (widthSpaceMode == MeasureSpec.AT_MOST && heightSpaceMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, defaultHeight)

        } else if (widthSpaceMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, heightSpaceSize)

        } else if (heightSpaceMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSpec, defaultHeight)
        }

        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthSpaceMode == MeasureSpec.AT_MOST && heightMeasureSpec == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measureWidth = childView.measuredWidth * childCount
            measureHeight = childView.measuredHeight
            setMeasuredDimension(measureWidth, measureHeight)
        } else if (heightSpaceMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measureHeight = childView.measuredHeight
            setMeasuredDimension(widthSpaceSize, childView.measuredHeight)
        } else if (widthSpaceMode == MeasureSpec.AT_MOST) {
            val childViwe = getChildAt(0)
            measureWidth = childViwe.measuredWidth * childCount
            setMeasuredDimension(measureWidth, heightSpaceSize)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var childLeft = 0
        val childCount = childCount
        mChildrenSize = childCount

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                val childWidth = childView.measuredWidth
                mChildWidth = childWidth
                childView.layout(childLeft, 0, childLeft + childWidth, childView.measuredHeight)
                childLeft += childWidth
            }
        }
    }

    private fun smoothScrollBy(dx: Int, dy: Int) {
        mScroller?.startScroll(scrollX, 0, dx, 0, 500)
        invalidate()
    }

    override fun computeScroll() {

        mScroller?.let {
            if (it.computeScrollOffset()) {
                scrollTo(it.currX, it.currY)
                postInvalidate()
            }
        }
    }

    override fun onDetachedFromWindow() {
        mVelocityTracker?.recycle()
        super.onDetachedFromWindow()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            mVelocityTracker?.addMovement(it)
            var x = it.getX()
            var y = it.getY()

            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (mScroller?.isFinished?.not()!!) {
                        mScroller?.abortAnimation()
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    var deltax = x - mLastX
                    var deltay = y - mLastY
                    scrollBy(-deltax.toInt(), 0)

                }
                MotionEvent.ACTION_UP -> {
                    val scrollx = scrollX
                    mVelocityTracker?.computeCurrentVelocity(1000)
                    var xVelocity = mVelocityTracker?.getXVelocity()

                    xVelocity?.let {
                        if (Math.abs(it) >= 50) {
                            if (it >= 0) {
                                mChildIndex = mChildIndex - 1
                            } else {
                                mChildIndex = mChildIndex + 1
                            }
                        } else {
                            mChildIndex = (scrollx + mChildWidth / 2) / mChildWidth
                        }

                        mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1))
                        var dx = mChildIndex * mChildWidth - scrollx
                        smoothScrollBy(dx, 0)
                    }
                    mVelocityTracker?.clear()
                }
                else -> {

                }
            }
            mLastX = x
            mLastY = y
            return true
        }
        return super.onTouchEvent(event)
    }
}