package com.example.transition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.transition.*
import com.example.transition.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scene1 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_1, this)
        scene2 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_2, this)

        binding.working.setOnCheckedChangeListener { _, checked ->
            when(checked) {
                R.id.student -> TransitionManager.go(scene1, ChangeBounds())
                R.id.worker -> TransitionManager.go(scene2, ChangeBounds())
            }
        }
    }
}