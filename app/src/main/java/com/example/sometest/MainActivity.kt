package com.example.sometest

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.sometest.util.CHANNEL_ID
import com.example.sometest.util.NetworkHelper

const val BLUETOOTH_TAG="Bluetooth"

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
	    companion object{
        var currentIssueSummary: String = ""
        var currentIssueId = ""
        var interceptorType:NetworkHelper.INTERCEPTOR_TYPE = NetworkHelper.INTERCEPTOR_TYPE.EMPTY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermissions(arrayOf(Manifest.permission.BLUETOOTH), 0)

        val intent = Intent(this, BtService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else startService(intent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, BtService::class.java))
        super.onDestroy()
    }
}