package com.example.sometest.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.OutputStream
import java.lang.reflect.Method
import java.util.*

class BtConnect:Thread() {
    private var mmSocket: BluetoothSocket? = null
    var m: Method? = null
    var outputStream: OutputStream? = null
    private val MY_UUID =
        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun ConnectThreadJava(device: BluetoothDevice) {
        try {
            m = device.javaClass.getMethod(
                "createInsecureRfcommSocketToServiceRecord",
                UUID::class.java
            )
        } catch (e: NoSuchMethodException) {
        }
        try {
            mmSocket = m!!.invoke(device, MY_UUID) as BluetoothSocket
        } catch (e: Exception) {
        }
        try {
            outputStream = mmSocket!!.outputStream
        } catch (ee: IOException) {
            Log.d(BLUETOOTH_TAG, "Failed to get output Stream")
        }
    }

    override fun run() {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (adapter.isDiscovering) {
            adapter.cancelDiscovery()
        }
        try {
            Log.d(BLUETOOTH_TAG, "Trying to connect with JavaThread")
            mmSocket!!.connect()
            outputStream!!.write(0)
        } catch (e: IOException) {
            try {
                mmSocket!!.close()
                Log.d(BLUETOOTH_TAG, "Closing socket in JavaThread")
            } catch (e1: IOException) {
            }
        }
    }

    @Throws(IOException::class)
    fun write(`val`: Int) { //        try{
//            Log.d(BLUETOOTH_TAG,"Trying to pass in javaThread"+val);
//            outputStream.write(val);
//        }catch (IOException e){Log.d(BLUETOOTH_TAG,"Pass Failed");
//        e.printStackTrace();}
        Log.d(BLUETOOTH_TAG, "Trying to pass in javaThread$`val`")
        outputStream!!.write(`val`)
        //        try{
//            mmSocket.close();
//        }catch (Exception e){}
    }
}