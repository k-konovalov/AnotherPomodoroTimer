package com.example.sometest.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import static com.example.sometest.MainActivityKt.BLUETOOTH_TAG;
import static com.example.sometest.MainActivityKt.getBluetoothAdapter;

public class ConnectThreadJava extends Thread {
    private BluetoothSocket mmSocket;
    Method m;
    OutputStream outputStream;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public ConnectThreadJava(BluetoothDevice device){
        try {
        m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);}
        catch (NoSuchMethodException e){}
        try {
        mmSocket=(BluetoothSocket) m.invoke(device,MY_UUID);}
        catch (Exception e){}
        try {
            outputStream=mmSocket.getOutputStream();
        }catch (IOException ee){Log.d(BLUETOOTH_TAG,"Failed to get output Stream");}
    }

    @Override
    public void run() {
        if(getBluetoothAdapter().isDiscovering()){
        getBluetoothAdapter().cancelDiscovery();}
        try {
            Log.d(BLUETOOTH_TAG,"Trying to connect with JavaThread");
            mmSocket.connect();
            outputStream.write(0);
        }catch (IOException e){
            try{
                mmSocket.close();
                Log.d(BLUETOOTH_TAG,"Closing socket in JavaThread");
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
        Log.d(BLUETOOTH_TAG,"Trying to pass in javaThread"+val);
        outputStream.write(val);
//        try{
//            mmSocket.close();
//        }catch (Exception e){}
    }
}
