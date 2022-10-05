package com.example.repositoryex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var myViewModel : MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startWorker).setOnClickListener {
            if (findViewById<EditText>(R.id.username).text.isNotEmpty()) {
                val username = findViewById<EditText>(R.id.username).text.toString()
                startWorker(username)
            }
        }
        findViewById<Button>(R.id.stopWorker).setOnClickListener { stopWorker() }

        myViewModel = ViewModelProvider(this, MyViewModel.Factory(this))[MyViewModel::class.java]

        myViewModel.repos.observe(this) { repos ->
            val response = StringBuilder().apply {
                repos.forEach {
                    append(it.name)
                    append(" - ")
                    append(it.owner)
                    append("\n")
                }
            }.toString()
            findViewById<TextView>(R.id.textResponse).text = response
        }

        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData(MyWorker.name)
            .observe(this) { workInfo ->
                if (workInfo.isNotEmpty()) {
                    when (workInfo[0].state) {
                        WorkInfo.State.ENQUEUED -> println("Worker enqueued!")
                        WorkInfo.State.RUNNING -> println("Worker running!")
                        WorkInfo.State.SUCCEEDED -> println("Worker succeeded!")  // only for one time worker
                        WorkInfo.State.CANCELLED -> println("Worker cancelled!")
                        else -> println(workInfo[0].state)
                    }
                }
            }
    }

    private fun startWorker(username: String) {
        val constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.UNMETERED)
            setRequiresBatteryNotLow(true)
        }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(workDataOf("username" to username))
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            MyWorker.name,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    private fun stopWorker() {
        WorkManager.getInstance(this).cancelUniqueWork(MyWorker.name)
    }
}