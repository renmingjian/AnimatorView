package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.erge.animatorview.R
import com.erge.animatorview.adapter.RVTestAdapter
import com.erge.animatorview.transition.CustomTransition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransitionActivity : AppCompatActivity() {

    private lateinit var fl_scene: FrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private var type: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)

        fl_scene = findViewById(R.id.fl_scene)
        val container: ConstraintLayout = fl_scene.findViewById(R.id.container)
        recyclerView = container.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RVTestAdapter(getData())
        scene1 = Scene(fl_scene, container)
        scene2 = Scene.getSceneForLayout(fl_scene, R.layout.scene2, this)
    }

    fun start(view: View) {
        type = if (type) {
            TransitionManager.go(scene1)
            false
        } else {
            TransitionManager.go(scene2)
            true
        }
    }

    private fun getData(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0..5) {
            list.add("item---$i")
        }
        return list
    }

    fun loading(view: View) {
        TransitionManager.go(scene2, null)
        val pb: ProgressBar = fl_scene.findViewById(R.id.pb)

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            delay(2000)
            pb.visibility = View.GONE
            delay(300)
            TransitionManager.go(scene1)
        }
    }
}