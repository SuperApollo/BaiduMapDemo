package com.example.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.fragment.MyFragment;
import com.example.myapplication.R;
import com.example.myapplication.utils.FragmentUtils;

public class MainActivity extends Activity {

    private TextView tv_show;
    private EditText et_input;
    private LinearLayout lv_container;
    private Button btn_send;

    public void setSendButtonListener(SendButtonListener sendButtonListener) {
        this.sendButtonListener = sendButtonListener;
    }

    private SendButtonListener sendButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentUtils.replace(getFragmentManager(),R.id.lv_container,new MyFragment());
        initView();

    }

    private void initView() {
        tv_show = (TextView) findViewById(R.id.tv_show);
        et_input = (EditText) findViewById(R.id.et_input);
        lv_container = (LinearLayout) findViewById(R.id.lv_container);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButtonListener.onClicked(et_input.getText().toString());
            }
        });
    }

    public interface SendButtonListener {
        void onClicked(String input);
    }
}
