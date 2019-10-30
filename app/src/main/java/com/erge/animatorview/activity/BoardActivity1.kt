package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.erge.animatorview.R
import com.erge.animatorview.view.BoardView

class BoardActivity1 : AppCompatActivity() {

    private lateinit var boardView: BoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board1)
        boardView = findViewById(R.id.boardView)
        boardView.setCount(50)
    }

    fun reset(view: View) {
        boardView.pointerAnim()
    }
}
