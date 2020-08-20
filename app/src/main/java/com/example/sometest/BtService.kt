package com.example.sometest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.sometest.util.CHANNEL_ID
import com.example.sometest.util.notificationId
import com.sirvar.bluetoothkit.BluetoothKit

class BtService : Service() {
    companion object {
        val bluetoothKit = BluetoothKit()
    }
    override fun onBind(intent: Intent): IBinder = Binder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bluetoothKit.run {
            //From Paired Devices
            getDeviceByName("HC-06")?.apply {
                connect(this) //Todo: Null here
            }
        }
        startForeground(notificationId, NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_settings_black_24)
            .setContentTitle("Pomodoro BT Connection")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        bluetoothKit.disconnect()
        stopForeground(true)
        super.onDestroy()
    }
}
