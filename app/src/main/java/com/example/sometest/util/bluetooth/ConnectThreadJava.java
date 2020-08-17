package com.example.sometest.util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.sometest.MainActivityKt;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class ConnectThreadJava extends Thread {
    private BluetoothSocket mmSocket;
    Method m;
    OutputStream outputStream;
    BluetoothAdapter bluetoothAdapter;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public ConnectThreadJava(BluetoothDevice device, BluetoothAdapter bluetoothAdapter){
        try {
        m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);}
        catch (NoSuchMethodException e){}
        try {
        mmSocket=(BluetoothSocket) m.invoke(device,MY_UUID);}
        catch (Exception e){}
        try {
            outputStream=mmSocket.getOutputStream();
        }catch (IOException ee){Log.d(MainActivityKt.BLUETOOTH_TAG,"Failed to get output Stream");}
    }

    @Override
    public void run() {
        if(bluetoothAdapter.isDiscovering()){ bluetoothAdapter.cancelDiscovery();}
        try {
            Log.d(MainActivityKt.BLUETOOTH_TAG,"Trying to connect with JavaThread");
            mmSocket.connect();
            outputStream.write(0);
        }catch (IOException e){
            try{
                mmSocket.close();
                Log.d(MainActivityKt.BLUETOOTH_TAG,"Closing socket in JavaThread");
            }catch (IOException e1){

            }
        }
    }
    public void write(Integer val) throws IOException{
//        try{
//            Log.d(BLUETOOTH_TAG,"Trying to pass in javaThread"+val);
//            outputStream.write(val);
//        }catch (IOException e){Log.d(BLUETOOTH_TAG,"Pass Failed");
//        e.printStackTrace();}
        Log.d(MainActivityKt.BLUETOOTH_TAG,"Trying to pass in javaThread"+val);
        outputStream.write(val);
//        try{
//            mmSocket.close();
//        }catch (Exception e){}
    }
}
