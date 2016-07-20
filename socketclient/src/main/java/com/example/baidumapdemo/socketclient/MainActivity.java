package com.example.baidumapdemo.socketclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends Activity {

    private Button btn_client;
    private ListView lv_fromserver;
    private String TAG = "MainActivity";
    private String PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSim();
        initView();
    }

    private void getSim() {
       TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PHONE = tm.getLine1Number();
    }

    private void initView() {
        btn_client = (Button) findViewById(R.id.btn_client);
        lv_fromserver = (ListView) findViewById(R.id.lv_fromserver);

       btn_client.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.e(TAG,"点击了按钮");
               new Thread(){
                   public void run() {
                       try {
                           Socket  client  = new Socket("192.168.1.111",4989);
                           OutputStream out= client.getOutputStream();
                           byte[] bytes = PHONE.getBytes("UTF-8");
                           if (client.isConnected()){
                               out.write(bytes,0,bytes.length);
                               out.flush();
                           }
                       } catch (UnknownHostException e) {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       } catch (IOException e) {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }

                   };
               }.start();
           }
       });
    }
}
