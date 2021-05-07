package com.erge.animatorview.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.abs

class HorizontalDragLayout(context: Context?, attrs: AttributeSet?) : FrameLayout(
    context!!, attrs
) {
    private var mLeftLayout: View? = null
    private var mRightLayout: View? = null
    private var mLeftWidth = 0
    private var mLeftHeight = 0
    private var mRightWidth = 0
    private var mRightHeight = 0
    private val mDragHelper: ViewDragHelper
    private var mState = State.CLOSE
    private val mDownX = 0f
    private val mDownY = 0f
    private var mViewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private var mIntercept = false
    private var mOnDragReleaseListener: OnDragReleaseListener? = null

    private val callback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(view: View, i: Int): Boolean {
            return view === mLeftLayout || view === mRightLayout
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return mRightWidth
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            // 根据滑动的对象View指定当前View的左右位置限制条件
            var left = left
            if (child === mLeftLayout) {
                if (left > 0) {
                    left = 0
                }
                if (left < -mRightWidth) {
                    left = -mRightWidth
                }
            } else {
                if (left < mLeftWidth - mRightWidth) {
                    left = mLeftWidth - mRightWidth
                }
                if (left > mLeftWidth) {
                    left = mLeftWidth
                }
            }
            return left
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            // 处理伴随滑动
            if (changedView === mLeftLayout) {
                val rightLayoutLeft = mRightLayout!!.left + dx
                mRightLayout!!.layout(
                    rightLayoutLeft,
                    top,
                    rightLayoutLeft + mRightWidth,
                    top + mRightHeight
                )
                if (mRightLayout is SlidBackView) {
                    (mRightLayout as SlidBackView).currentWidth = (right - rightLayoutLeft).toFloat()
                }
            } else {
                val leftLayoutLeft = mLeftLayout!!.left + dx
                mLeftLayout!!.layout(leftLayoutLeft, top, leftLayoutLeft + mLeftWidth, mLeftHeight)
            }
            if (mLeftLayout!!.left == -mRightWidth && mState == State.CLOSE) {
                mState = State.OPEN
            } else if (mLeftLayout!!.left == 0 && mState == State.OPEN) {
                mState = State.CLOSE
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            if (abs(xvel) > mViewConfiguration.scaledMinimumFlingVelocity) {
                if (xvel > 0) {
                    close()
                } else {
                    open()
                }
            } else {
                if (mLeftLayout!!.left < -mRightWidth / 2) {
                    open()
                } else {
                    close()
                }
            }
            if (mState == State.OPEN) {
                if (mOnDragReleaseListener != null) {
                    mOnDragReleaseListener!!.onRelease(mState)
                    state = State.CLOSE
                }
            }
        }
    }

    init {
        mDragHelper = ViewDragHelper.create(this, callback)
    }

    enum class State {
        // 打开状态，即向左滑动使右侧部分完全显示出来
        OPEN,  // 关闭状态，无滑动时的正常显示状态
        CLOSE
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mLeftLayout = getChildAt(0)
        if (childCount > 1) {
            mRightLayout = getChildAt(1) as SlidBackView
        } else {
            mRightLayout = View(context)
        }
        if (mLeftLayout is RecyclerView) {
            val recyclerView = mLeftLayout as RecyclerView
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager
                    val itemCount = Objects.requireNonNull(recyclerView.adapter).itemCount
                    if (layoutManager is LinearLayoutManager) {
                        val lastCompletelyVisibleItemPosition =
                            layoutManager.findLastCompletelyVisibleItemPosition()
                        mIntercept = lastCompletelyVisibleItemPosition == itemCount - 1
                    }
                }
            })
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 获取左右两部分布局的宽高
        mLeftWidth = mLeftLayout!!.measuredWidth
        mLeftHeight = mLeftLayout!!.measuredHeight
        mRightWidth = mRightLayout!!.measuredWidth
        mRightHeight = mRightLayout!!.measuredHeight
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // 摆放两个布局的初始化位置
        mLeftLayout!!.layout(0, 0, mLeftWidth, mLeftHeight)
        mRightLayout!!.layout(mLeftWidth, 0, mLeftWidth + mRightWidth, mRightHeight)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mIntercept && mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
//                switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownX = event.getX();
//                mDownY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveX = event.getX();
//                float moveY = event.getY();
//                // 如果是横向滑动，则不让RecyclerView或ListView等滑动控件拦截事件，自己来处理
//                if (Math.abs(moveX - mDownX) > Math.abs(moveY - mDownY)) {
//                    requestDisallowInterceptTouchEvent(true);
//                }
//                break;
//        }
        return true
    }

    fun open() {
        mState = State.OPEN
        mDragHelper.smoothSlideViewTo(mLeftLayout!!, -mRightWidth, 0)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    fun close() {
        mState = State.CLOSE
        mDragHelper.smoothSlideViewTo(mLeftLayout!!, 0, 0)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    var state: State
        get() = mState
        set(state) {
            mState = state
            if (mState == State.OPEN) {
                open()
            } else {
                close()
            }
        }

    fun setOnDragReleaseListener(onDragReleaseListener: OnDragReleaseListener?) {
        mOnDragReleaseListener = onDragReleaseListener
    }

    interface OnDragReleaseListener {
        fun onRelease(state: State?)
    }
}