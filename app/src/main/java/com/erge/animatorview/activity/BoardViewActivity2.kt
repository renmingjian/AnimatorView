package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.erge.animatorview.R
import com.erge.animatorview.view.BoardView2

class BoardViewActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view2)

        val boardView2 = findViewById<BoardView2>(R.id.boardView)
        findViewById<TextView>(R.id.btn_reset).setOnClickListener {
            boardView2.startAnim(70f)
        }
    }
}
