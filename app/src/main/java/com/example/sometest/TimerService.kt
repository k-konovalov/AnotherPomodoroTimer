package com.example.sometest

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import com.example.sometest.timer.TimerViewModel
import com.example.sometest.util.CHANNEL_ID
import com.example.sometest.util.notificationId

class TimerService : Service(), LifecycleObserver {
    //Total time of the session
    companion object{
        var cycle: Int = 0
        lateinit var timer: CountDownTimer
        lateinit var builder: NotificationCompat.Builder
    }

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_settings_black_24)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Doing some work...")
            .build()
        startForeground(notificationId,notification)

        return super.onStartCommand(intent, flags, startId)
    }
}
