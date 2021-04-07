package com.erge.animatorview.activity

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.erge.animatorview.R
import com.erge.animatorview.adapter.TagRvAdapter
import com.erge.animatorview.bean.MerchantImage
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
            add(
                TagLocation(
                    200f,
                    100f,
                    "tips",
                    offset = Utils.dp2px(10f),
                    leftMargin = Utils.dp2px(10f),
                    rightMargin = Utils.dp2px(10f)
                )
            )
            add(
                TagLocation(
                    900f,
                    300f,
                    "links_merchant_links_merchant_links_merchant_links_merchant",
                    offset = Utils.dp2px(10f),
                    leftMargin = Utils.dp2px(10f),
                    rightMargin = Utils.dp2px(10f)
                )
            )
            add(
                TagLocation(
                    100f,
                    800f,
                    "You Love Me! HaHa! Yes",
                    offset = Utils.dp2px(10f),
                    leftMargin = Utils.dp2px(10f),
                    rightMargin = Utils.dp2px(10f)
                )
            )
        }
    }

    private fun getData2(): MutableList<TagLocation> {
        return mutableListOf<TagLocation>().apply {
            add(
                TagLocation(
                    300f,
                    100f,
                    "my name",
                    offset = Utils.dp2px(10f),
                    leftMargin = Utils.dp2px(10f),
                    rightMargin = Utils.dp2px(10f)
                )
            )
            add(
                TagLocation(
                    400f,
                    600f,
                    "happy new year",
                    offset = Utils.dp2px(10f),
                    leftMargin = Utils.dp2px(10f),
                    rightMargin = Utils.dp2px(10f)
                )
            )
        }
    }

    private fun getData3(): MutableList<TagLocation> {
        return mutableListOf<TagLocation>().apply {
            add(
                TagLocation(
                    300f,
                    100f,
                    "Hello Kit",
                    offset = Utils.dp2px(10f),
                    leftMargin = Utils.dp2px(10f),
                    rightMargin = Utils.dp2px(10f)
                )
            )
        }
    }

    private fun getImage1(): MutableList<MerchantImage> {
        return mutableListOf<MerchantImage>().apply {
            add(MerchantImage(1000, 972, "https://img0.baidu.com/it/u=1505799353,2691569779&fm=26&fmt=auto&gp=0.jpg", "Good Like1", link = "123", list = getData1()))
            add(MerchantImage(820, 512, "https://img1.baidu.com/it/u=3032317551,3314409878&fm=26&fmt=auto&gp=0.jpg", "Good Like2", link = "http:/www.erye.com", list = getData2()))
            add(MerchantImage(658, 987, "https://img2.baidu.com/it/u=3576358313,3239146876&fm=26&fmt=auto&gp=0.jpg", "Good Like3", list = getData1()))
            add(MerchantImage(360, 360, "https://img0.baidu.com/it/u=1563838440,2025992817&fm=11&fmt=auto&gp=0.jpg", "Good Like4", list =  null))
        }
    }

    private fun getImage2(): MutableList<MerchantImage> {
        return mutableListOf<MerchantImage>().apply {
            add(MerchantImage(800, 800, "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=39604184,2683877292&fm=26&gp=0.jpg", "Good Like", list = getData3()))
        }
    }

    private fun getImage3(): MutableList<MerchantImage> {
        return mutableListOf<MerchantImage>().apply {
            add(MerchantImage(800, 756, "https://img0.baidu.com/it/u=2778529950,2184146500&fm=26&fmt=auto&gp=0.jpg","Buy Now", list =  getData1()))
            add(MerchantImage(761, 1060, "https://img0.baidu.com/it/u=3220721734,2959137348&fm=26&fmt=auto&gp=0.jpg", "Show Me Now", list =  getData1()))
        }
    }

    private fun getData(): MutableList<MerchantItem> {
        return mutableListOf<MerchantItem>().apply {
            add(
                MerchantItem(
                    455,
                    "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了",
                    imgList = getImage1()
                )
            )
            add(
                MerchantItem(
                    533,
                    "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了",
                    imgList = getImage2()
                )
            )
//            add(
//                MerchantItem(
//                    123,
//                    "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了",
//                    imgList = getImage3()
//                )
//            )
//            add(
//                MerchantItem(
//                    33,
//                    "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了",
//                    imgList = getImage1()
//                )
//            )
//            add(
//                MerchantItem(
//                    453,
//                    "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了",
//                    imgList = getImage2()
//                )
//            )
//            add(
//                MerchantItem(
//                    353,
//                    "大捡垃圾 俺家了大姐夫安静阿里斯顿解放啦婕拉垃圾袋弗拉就拉上来的安静的拉大链接啊来得及法律就打狙手抖放假案例三等奖发健身砥砺奋进啊了",
//                    imgList = getImage2()
//                )
//            )
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