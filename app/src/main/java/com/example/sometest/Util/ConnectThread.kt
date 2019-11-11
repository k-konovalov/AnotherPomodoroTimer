package com.example.sometest.Util

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.example.sometest.BLUETOOTH_TAG
import com.example.sometest.MY_UUID
import com.example.sometest.bluetoothAdapter
import java.io.IOException

class ConnectThread(device: BluetoothDevice) : Thread() {

//    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
//        device.createRfcommSocketToServiceRecord(MY_UUID)
//    }
    val mmSocket = device.createRfcommSocketToServiceRecord(MY_UUID)
    private val outStream=mmSocket.outputStream
    override fun run() {
        if (mmSocket ==null )Log.d(BLUETOOTH_TAG,"WTF!!!!!!!")
        bluetoothAdapter?.cancelDiscovery()
        try{
            mmSocket.connect()
        }catch (e:IOException){
            try {
                mmSocket.close()
                Log.d(BLUETOOTH_TAG,"Socket closed")
            } catch (e:IOException){
                Log.d(BLUETOOTH_TAG,"Unable to create socket during collection failure",e)
            }
        }
    }
    fun cancel() {
        try {
            mmSocket.close()
        } catch (e: IOException) {
            Log.e(BLUETOOTH_TAG, "Could not close the client socket", e)
        }
    }
    fun write(value: Int){
        Log.d(BLUETOOTH_TAG,"Trying to write ${value}")
        try {
            outStream.write(value)
        }catch (e:IOException){Log.d(BLUETOOTH_TAG,"")}
    }
}