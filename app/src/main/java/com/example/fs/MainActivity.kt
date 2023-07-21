package com.example.fs


import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val myReceiver = MyReceiver()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
            0
        )
        setContentView(R.layout.activity_main)

        val playButton = findViewById<FloatingActionButton>(R.id.fabPlay)
        val stopButton = findViewById<FloatingActionButton>(R.id.fabStop)
        val pauseButton = findViewById<FloatingActionButton>(R.id.fabPause)
        registerReceiver(myReceiver, IntentFilter("MY_ACTION"))

        playButton.setOnClickListener {
            Intent(applicationContext, RunningService::class.java).also {
                it.action = RunningService.Actions.STARTED.toString()
                startService(it)
            }
        }

        stopButton.setOnClickListener {
            Intent(applicationContext, RunningService::class.java).also {
                it.action = RunningService.Actions.STOPPED.toString()
                startService(it)
            }
        }

        pauseButton.setOnClickListener {
            Intent(applicationContext, RunningService::class.java).also {
                it.action = RunningService.Actions.PAUSED.toString()
                startService(it)
            }
        }
    }

    override fun onStart(){
        super.onStart()
        Intent(applicationContext, RunningService::class.java).also {
            it.action = RunningService.Actions.STARTED.toString()
            startService(it)
        }
    }

    override fun onStop(){
        super.onStop()
        Intent(applicationContext, RunningService::class.java).also {
            it.action = RunningService.Actions.STOPPED.toString()
            startService(it)
        }
    }

    override fun onPause() {
        super.onPause()
        Intent(applicationContext, RunningService::class.java).also {
            it.action = RunningService.Actions.PAUSED.toString()
            startService(it)
        }
        unregisterReceiver(myReceiver)
    }
}