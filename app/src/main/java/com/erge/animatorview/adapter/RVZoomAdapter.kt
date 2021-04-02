package com.erge.animatorview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.erge.animatorview.R
import com.erge.animatorview.bean.ZoomData

/**
 * ZoomHelper可以对某一个View进行缩放，内部是通过Activity招到content这个View，然后递归遍历每一个子View，判断该View
 * 是不是要缩放的View，判断的条件就是该View是不是有一个id为zoomable的tag，如果是，则该View就是要缩放的View，此时直接
 * return，即使还有其他View也设置了id为zoomable，也不再执行。
 * 所以，如果在一个item中，根据条件不同，对不同的View做缩放，需要根据条件判断实现缩放，有两种方式：
 * ①在xml中为每一个View设置tag，但是在代码中根据条件判断设置只有当前缩放的View可见，其他有tag的可缩放View不可见，不然
 * 会出现问题
 * ②不在xml中设置tag，而是在代码中根据条件判断，只给当前缩放的View设置可缩放：ZoomHelper.addZoomableView(iv_bg)
 * Created by erge 3/29/21 4:59 PM
 */
class RVZoomAdapter(val data: MutableList<ZoomData>) :
    RecyclerView.Adapter<RVZoomAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tv_name: TextView = itemView.findViewById(R.id.tv_name)
        private var iv_bg: ImageView = itemView.findViewById(R.id.iv_bg)
        private var iv_1: ImageView = itemView.findViewById(R.id.iv_1)
        private var iv_2: ImageView = itemView.findViewById(R.id.iv_2)

        init {
            iv_bg.setOnClickListener {
                Toast.makeText(itemView.context, "click", Toast.LENGTH_SHORT).show()
            }
        }

        fun bindData(data: ZoomData) {
            tv_name.text = data.name
            when (data.type) {
                0 -> {
                    iv_bg.visibility = View.GONE
                    iv_1.visibility = View.VISIBLE
                    iv_2.visibility = View.GONE
//                    ZoomHelper.addZoomableView(iv_1)
                    iv_1.setImageResource(R.mipmap.programmer)
                }
                1 -> {
                    iv_bg.visibility = View.VISIBLE
                    iv_1.visibility = View.GONE
                    iv_2.visibility = View.GONE
//                    ZoomHelper.addZoomableView(iv_bg)
                    iv_bg.setImageResource(R.mipmap.ic_launcher)
                }
                2 -> {
                    iv_bg.visibility = View.GONE
                    iv_1.visibility = View.GONE
//                    ZoomHelper.addZoomableView(iv_bg)
                    iv_2.setImageResource(R.mipmap.button_panel1)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item2, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

}