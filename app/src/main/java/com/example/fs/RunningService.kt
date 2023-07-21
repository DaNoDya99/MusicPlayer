package com.example.fs

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.widget.SeekBar
import androidx.core.app.NotificationCompat

class RunningService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

//    If multiple apps need to connect with the one single service then you need to use IBinder
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

//    This method is called when the service is started using startService()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.STARTED.toString() -> {
                start()
            }
            Actions.STOPPED.name -> {
                mediaPlayer.stop()
                mediaPlayer.release()
                stopSelf()
            }
            Actions.PAUSED.name -> {
                mediaPlayer.pause()
            }
            Actions.RESUMED.name -> {
                mediaPlayer.start()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    enum class Actions {
        STARTED,
        STOPPED,
        PAUSED,
        RESUMED
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setContentTitle("Music Player")
            .setContentText("Music is playing in the background")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(1, notification)

        startMediaPlayer()
    }

    private fun startMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.song)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }


}