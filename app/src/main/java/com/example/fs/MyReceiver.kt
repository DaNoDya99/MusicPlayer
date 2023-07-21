package com.example.fs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "MY_ACTION") {
            Intent(context, RunningService::class.java).also {
                it.action = RunningService.Actions.PAUSED.toString()
                context.startService(it)
            }
        }
    }
}