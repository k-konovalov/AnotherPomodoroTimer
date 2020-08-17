package com.example.sometest

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sometest.util.bluetooth.ConnectThread
import com.example.sometest.util.bluetooth.ConnectThreadJava
import java.lang.Exception

class MainViewModel: ViewModel() {
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter() ?: throw Exception("No Bluetooth in Device")
    private val list = mutableListOf<BluetoothDevice>()
    private val mConnectThread: ConnectThread by lazy { ConnectThread(bluetoothAdapter.getRemoteDevice("98:D3:21:F4:80:86"), bluetoothAdapter::cancelDiscovery) }
    private val jConnectThread: ConnectThreadJava by lazy { ConnectThreadJava(bluetoothAdapter.getRemoteDevice("98:D3:21:F4:80:86"), bluetoothAdapter) }

    val deviceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    Log.d(BLUETOOTH_TAG,"Action found")
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.run {
                        Log.d(BLUETOOTH_TAG, "Device name: $name Device mac: $address")
                        list.add(device)
                    }
                }
            }
        }
    }
    val bTReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            action?.run {
                if (equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
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
    }
    val discoverReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            action?.run {
                if (equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                    val mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR)
                    when(mode){
                        BluetoothAdapter.SCAN_MODE_CONNECTABLE -> Log.d(BLUETOOTH_TAG,"DISCOVERABILITY DISABLED. Able to receive connections")
                        BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> Log.d(BLUETOOTH_TAG,"Discoverability enabled")
                        BluetoothAdapter.SCAN_MODE_NONE -> Log.d(BLUETOOTH_TAG,"Discoverability disabled. Unable to receive connections")
                        BluetoothAdapter.STATE_CONNECTING-> Log.d(BLUETOOTH_TAG,"Connecting...")
                        BluetoothAdapter.STATE_CONNECTED-> Log.d(BLUETOOTH_TAG,"Connected")
                    }
                }
            }
        }
    }
    val btState = MutableLiveData<Pair<Intent,Int>>()

    fun prepareBt(){
        //if (bluetoothAdapter==null) Toast.makeText(this,"Устройство не поддерживает bluetooth.", Toast.LENGTH_SHORT).show()
        bluetoothAdapter.run {
            if (isEnabled) {
                btState.postValue(
                    Pair(
                        Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                        REQUEST_ENABLE_BT
                    )
                )

                startDiscovery()
            }

            val discoverIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 10)

            btState.postValue(
                Pair(
                    discoverIntent,
                    REQUEST_DISCOVER_DEVICES
                )
            )

            val pairedDevices = bondedDevices
            pairedDevices?.forEach { device ->
                Log.d(BLUETOOTH_TAG, "Paired " + device.name + " Address: " + device.address)
            }
            startDiscovery()

            mConnectThread.run()
            mConnectThread.write(0)

            //jConnectThread.run()
            //jConnectThread.write(0)
        }
    }
}