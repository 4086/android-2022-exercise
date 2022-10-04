package com.example.transitionaddex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import com.example.transitionaddex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        scene1 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_1, this)
        scene2 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_2, this)

        binding.working.setOnCheckedChangeListener { _, checked ->
            when(checked) {
                R.id.student -> {
                    TransitionManager.go(scene1, ChangeBounds())
                    val nameEditText1 = findViewById<EditText>(R.id.et_id1)
                    nameEditText1.setText(viewModel.str1)
                    val nameEditText2 = findViewById<EditText>(R.id.et_id2)
                    nameEditText2.setText(viewModel.str2)
                    val radioGroup = findViewById<RadioGroup>(R.id.num)
                    if(viewModel.checked > 0) {
                        when(viewModel.checked) {
                            1 -> radioGroup.check(R.id.no1)
                            2 -> radioGroup.check(R.id.no2)
                            3 -> radioGroup.check(R.id.no3)
                            4 -> radioGroup.check(R.id.no4)
                        }
                    }
                }
                R.id.worker -> {
                    val nameEditText1 = findViewById<EditText>(R.id.et_id1)
                    viewModel.str1 = nameEditText1.text.toString()
                    val nameEditText2 = findViewById<EditText>(R.id.et_id2)
                    viewModel.str2 = nameEditText2.text.toString()
                    val radioGroup = findViewById<RadioGroup>(R.id.num)
                    when(radioGroup.checkedRadioButtonId){
                        R.id.no1 -> viewModel.checked = 1
                        R.id.no2 -> viewModel.checked = 2
                        R.id.no3 -> viewModel.checked = 3
                        R.id.no4 -> viewModel.checked = 4
                    }
                    TransitionManager.go(scene2, ChangeBounds())
                }
            }
        }
    }
}
