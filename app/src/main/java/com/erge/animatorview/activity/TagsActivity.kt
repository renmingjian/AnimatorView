package com.erge.animatorview.activity

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.erge.animatorview.R
import com.erge.animatorview.adapter.RVZoomAdapter
import com.erge.animatorview.adapter.TagRvAdapter
import com.erge.animatorview.bean.MerchantItem
import com.erge.animatorview.bean.TagLocation
import com.erge.animatorview.utils.Utils

class TagsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TagRvAdapter(getData())
    }

    private fun getData1(): MutableList<TagLocation> {
        return mutableListOf<TagLocation>().apply {
            add(TagLocation(200f, 200f, "tips", offset = Utils.dp2px(10f), leftMargin = Utils.dp2px(10f), rightMargin = Utils.dp2px(10f)))
            add(TagLocation(1000f, 300f, "links_merchant", offset = Utils.dp2px(10f), leftMargin = Utils.dp2px(10f), rightMargin = Utils.dp2px(10f)))
            add(TagLocation(100f, 800f, "You Love Me! HaHa! Yes", offset = Utils.dp2px(10f), leftMargin = Utils.dp2px(10f), rightMargin = Utils.dp2px(10f)))
        }
    }

    private fun getData2(): MutableList<TagLocation> {
        return mutableListOf<TagLocation>().apply {
            add(TagLocation(300f, 100f, "my name", offset = Utils.dp2px(10f), leftMargin = Utils.dp2px(10f), rightMargin = Utils.dp2px(10f)))
            add(TagLocation(400f, 600f, "happy new year", offset = Utils.dp2px(10f), leftMargin = Utils.dp2px(10f), rightMargin = Utils.dp2px(10f)))
        }
    }

    private fun getData(): MutableList<MerchantItem> {
        return mutableListOf<MerchantItem>().apply {
            add(MerchantItem(455, "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了", getData1()))
            add(MerchantItem(533, "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了", getData2()))
            add(MerchantItem(123, "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了", getData1()))
            add(MerchantItem(33, "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了", getData2()))
            add(MerchantItem(33, "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了", getData1()))
            add(MerchantItem(33, "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了", getData2()))
        }
    }

    fun test(view: View) {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
    }

    fun image(view: View) {
        Toast.makeText(this, "image", Toast.LENGTH_SHORT).show()
    }

    fun imageClick(view: View) {
        Toast.makeText(this, "image", Toast.LENGTH_SHORT).show()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ZoomHelper.getInstance().dispatchTouchEvent(ev!!, this) || super.dispatchTouchEvent(
            ev
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ZoomHelper.getInstance().release()
        ZoomHelper.getInstance().dismiss()
    }
}