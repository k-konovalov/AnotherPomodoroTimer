package com.example.sometest

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.bluetooth.BluetoothSocket
import android.content.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sometest.timer.TimerViewModel
import com.example.sometest.util.*
import com.example.sometest.util.bluetooth.ConnectThread
import com.example.sometest.util.bluetooth.ConnectThreadJava
import java.util.*

var socket: BluetoothSocket? = null
val bluetoothAdapter:BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
lateinit var mConnectThread : ConnectThread
lateinit var jConnectThread: ConnectThreadJava
lateinit var myDevice:BluetoothDevice
val REQUEST_ENABLE_BT = 1
val REQUEST_DISCOVER_DEVICES=2
const val BLUETOOTH_TAG="Bluetooth"
val MY_UUID = UUID.randomUUID()

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var context:Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        context = applicationContext
        /*if(bluetoothAdapter==null)
            Toast.makeText(this,"Устройство не поддерживает bluetooth.", Toast.LENGTH_SHORT)
        val bTfilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bTReceiver,bTfilter)
        if(bluetoothAdapter!=null && !bluetoothAdapter.isEnabled)
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT)

        bluetoothAdapter!!.startDiscovery()

        val deviceFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(deviceReceiver, deviceFilter)

        val discoverFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        registerReceiver(discoverReceiver,discoverFilter)

        val discoverIntent=Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,10)
        startActivityForResult(discoverIntent, REQUEST_DISCOVER_DEVICES)

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            Log.d(BLUETOOTH_TAG,"Paired "+device.name+" Address: "+device.address)
        }
        bluetoothAdapter!!.startDiscovery()
        mConnectThread = ConnectThread(bluetoothAdapter.getRemoteDevice("98:D3:21:F4:80:86"))
        mConnectThread.run()
        jConnectThread = ConnectThreadJava(
            bluetoothAdapter!!.getRemoteDevice("98:D3:21:F4:80:86")
        )
        jConnectThread.run()
        jConnectThread.write(0)*/



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

    /*    var list:MutableList<BluetoothDevice> = mutableListOf()
    private val deviceReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    Log.d(BLUETOOTH_TAG,"Action found")
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    Log.d(BLUETOOTH_TAG,"Device name: "+device.name.toString()+" Device mac: "+device.address.toString())
                    list.add(device)
                }
            }
        }
    }
    private val bTReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR)
                when(state){
                    BluetoothAdapter.STATE_OFF -> Log.d(BLUETOOTH_TAG,"STATE OFF")
                    BluetoothAdapter.STATE_TURNING_OFF -> Log.d(BLUETOOTH_TAG,"TURNING OFF")
                    BluetoothAdapter.STATE_TURNING_ON -> Log.d(BLUETOOTH_TAG,"TURNING ON")
                    BluetoothAdapter.STATE_ON -> Log.d(BLUETOOTH_TAG,"STATE ON")
                }
            }
        }
    }
    private val discoverReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            val action = intent!!.action
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                val mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR)
                when(mode){
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE ->Log.d(BLUETOOTH_TAG,"DISCOVERABILITY DISABLED. Able to receive connections")
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE ->Log.d(BLUETOOTH_TAG,"Discoverability enabled")
                    BluetoothAdapter.SCAN_MODE_NONE ->Log.d(BLUETOOTH_TAG,"Discoverability disabled. Unable to receive connections")
                    BluetoothAdapter.STATE_CONNECTING->Log.d(BLUETOOTH_TAG,"Connecting...")
                    BluetoothAdapter.STATE_CONNECTED->Log.d(BLUETOOTH_TAG,"Connected")
                }
            }
        }
    }
    */
}