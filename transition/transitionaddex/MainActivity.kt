package com.example.transitionaddex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        var nameEditText1 = findViewById<EditText>(R.id.et_id1)
        var nameEditText2 = findViewById<EditText>(R.id.et_id2)
        var radioGroup = findViewById<RadioGroup>(R.id.num)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.str1 = nameEditText1.text.toString()
        viewModel.str2 = nameEditText2.text.toString()

        nameEditText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                viewModel.str1 = nameEditText1.text.toString()
            }
        })

        nameEditText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                viewModel.str2 = nameEditText2.text.toString()
            }
        })

        radioGroup.setOnCheckedChangeListener { group, checked ->
            when(checked){
                R.id.no1 -> viewModel.checked = 1
                R.id.no2 -> viewModel.checked = 2
                R.id.no3 -> viewModel.checked = 3
                R.id.no4 -> viewModel.checked = 4
            }
        }

        scene1 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_1, this)
        scene2 = Scene.getSceneForLayout(binding.sceneRoot, R.layout.scene_2, this)

        binding.working.setOnCheckedChangeListener { _, checked ->
            when(checked) {
                R.id.student -> {
                    TransitionManager.go(scene1, ChangeBounds())
                    val neEText1 = findViewById<EditText>(R.id.et_id1)
                    neEText1.setText(viewModel.str1)
                    val neEText2 = findViewById<EditText>(R.id.et_id2)
                    neEText2.setText(viewModel.str2)
                    var rGroup = findViewById<RadioGroup>(R.id.num)
                    if(viewModel.checked > 0) {
                        when(viewModel.checked) {
                            1 -> rGroup.check(R.id.no1)
                            2 -> rGroup.check(R.id.no2)
                            3 -> rGroup.check(R.id.no3)
                            4 -> rGroup.check(R.id.no4)
                        }
                    }
                }
                R.id.worker -> TransitionManager.go(scene2, ChangeBounds())
            }
        }
    }
}