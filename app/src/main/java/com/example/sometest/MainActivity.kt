package com.example.sometest


import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.content.*
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sometest.util.*
import com.example.sometest.util.bluetooth.ConnectThread
import com.example.sometest.util.bluetooth.ConnectThreadJava
import java.util.*
val REQUEST_ENABLE_BT = 1
val REQUEST_DISCOVER_DEVICES=2
const val BLUETOOTH_TAG="Bluetooth"

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
	    companion object{
        var currentIssueSummary: String = ""
        lateinit var context:Context
        var currentIssueId = ""
        var interceptorType:NetworkHelper.INTERCEPTOR_TYPE = NetworkHelper.INTERCEPTOR_TYPE.EMPTY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermissions(arrayOf(Manifest.permission.BLUETOOTH), 0)
        registerBtReceiver()
        viewModel.prepareBt()
        viewModel.btState.observe(this, androidx.lifecycle.Observer {
            startActivityForResult(
                it.first, it.second
            )
        })
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

    private fun registerBtReceiver(){
        val bTfilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        val deviceFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val discoverFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)

        registerReceiver(viewModel.deviceReceiver, deviceFilter)
        registerReceiver(viewModel.discoverReceiver, discoverFilter)
        registerReceiver(viewModel.bTReceiver, bTfilter)
    }
}