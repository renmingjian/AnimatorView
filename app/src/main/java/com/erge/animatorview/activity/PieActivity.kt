package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erge.animatorview.R
import com.erge.animatorview.view.UserLevelPieView
import java.util.*

class PieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie)

        val userLevelPieView:UserLevelPieView  = findViewById(R.id.userLevelPieView)

        val data = LinkedList<UserLevelPieView.LevelPieInfo>()
        val info1 = UserLevelPieView.LevelPieInfo()
        info1.color = "#ff0000"
        info1.count = 2500
        info1.level = "荣耀卡哈哈哈"
        data.add(info1)

        val info2 = UserLevelPieView.LevelPieInfo()
        info2.color = "#00ff00"
        info2.count = 4500
        info2.level = "战神卡"
        data.add(info2)

        val info3 = UserLevelPieView.LevelPieInfo()
        info3.color = "#0000ff"
        info3.count = 1500
        info3.level = "专家卡厉害不"
        data.add(info3)

        val info4 = UserLevelPieView.LevelPieInfo()
        info4.color = "#e7e7e7"
        info4.count = 8000
        info4.level = "菜逼卡"
        data.add(info4)
        userLevelPieView.setData(data)
    }
}
