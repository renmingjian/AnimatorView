
package com.erge.animatorview.adapter

import android.animation.ObjectAnimator
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.erge.animatorview.R
import com.erge.animatorview.bean.MerchantImage
import com.erge.animatorview.bean.MerchantItem
import com.erge.animatorview.utils.TagLocationProvider3

class InspirationBannerAdapter2(val datas: MutableList<MerchantImage>, val lineView: View) :
    PagerAdapter() {

    val viewHolderMap = mutableMapOf<Int, BannerViewHolder>()
    private var prePosition: Int = -1
    private val viewList = mutableMapOf<Int, View>()

    override fun getCount(): Int {
        return datas.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.item_merchant_image, container, false)
        viewList[position] = view
        val holder = BannerViewHolder(view)
        viewHolderMap[position] = holder
        holder.bindData(datas[position], position)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewList[position])
    }

    fun anim(currentPosition: Int) {
        val currentHolder = viewHolderMap[currentPosition]
        currentHolder?.linkAnim()
        currentHolder?.tagAnim()
        prePosition = currentPosition
    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.iv_merchant)
        private val tvLink: TextView = itemView.findViewById(R.id.tv_link)
        private val clLink: View = itemView.findViewById(R.id.cl_link)
        private val flContainer: FrameLayout = itemView.findViewById(R.id.fl_container)
        private var data: MerchantImage? = null
        private val provider = TagLocationProvider3(lineView) {
            Toast.makeText(itemView.context, it.name, Toast.LENGTH_SHORT).show()
        }

        init {
            ZoomHelper.addZoomableView(imageView)
            ZoomHelper.getInstance().addOnZoomStateChangedListener(object :
                ZoomHelper.OnZoomStateChangedListener {
                override fun onZoomStateChanged(
                    zoomHelper: ZoomHelper,
                    zoomableView: View,
                    isZooming: Boolean
                ) {
                    if (isZooming) {
                        flContainer.visibility = View.INVISIBLE
                    } else {
                        flContainer.visibility = View.VISIBLE
                    }
                }
            })
            flContainer.setOnClickListener {
                Toast.makeText(itemView.context, data?.imgUrl, Toast.LENGTH_SHORT).show()
            }
            clLink.setOnClickListener {
                Toast.makeText(itemView.context, data?.linkName, Toast.LENGTH_SHORT).show()
            }
        }

        fun bindData(data: MerchantImage, position: Int) {
            this.data = data
            val layoutParams = imageView.layoutParams
            layoutParams.width = data.width
            layoutParams.height = data.height
            imageView.layoutParams = layoutParams

            Glide.with(imageView.context).load(data.imgUrl).into(imageView)
            if (TextUtils.isEmpty(data.linkName) || TextUtils.isEmpty(data.link)) {
                clLink.visibility = View.GONE
            } else {
                if (data.animated) {
                    clLink.visibility = View.VISIBLE
                } else {
                    clLink.visibility = View.INVISIBLE
                }
            }
            tvLink.text = data.linkName
            val labels = data.list
            if (labels != null && labels.size > 0) {
                // 设置tag
                val flContainerLayoutParams = flContainer.layoutParams
                flContainerLayoutParams.width = data.width
                flContainerLayoutParams.height = data.height
                flContainer.layoutParams = flContainerLayoutParams
                flContainer.visibility = View.VISIBLE
                flContainer.removeAllViews()
                provider.addView(labels, flContainer)
            } else {
                flContainer.visibility = View.INVISIBLE
            }

            imageView.postDelayed({
                println("imageWidth = ${imageView.width}")
            }, 100)
        }

        fun tagAnim() {
            val labels = data?.list
            if (labels != null && labels.size > 0) {
                for ((index, value) in labels.withIndex()) {
                    provider.anim(value, flContainer.getChildAt(index))
                }
            }
        }

        fun linkAnim() {
            // 没有link内容，则不显示
            val animated = data?.animated ?: false
            if (animated || TextUtils.isEmpty(data?.linkName) || TextUtils.isEmpty(data?.link)) return
            val lineLocationArray = IntArray(2)
            val linkLocationArray = IntArray(2)

            lineView.getLocationInWindow(lineLocationArray)
            clLink.getLocationInWindow(linkLocationArray)
            if (linkLocationArray[1] + clLink.height / 2 > lineLocationArray[1]) return
            clLink.visibility = View.VISIBLE
            data?.animated = true
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                clLink,
                "alpha",
                0f,
                1f
            )
            animator.duration = 500
            animator.start()
        }
    }


}