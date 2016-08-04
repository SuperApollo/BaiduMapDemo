package com.example.mymodule;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int REFRESH_TV = 0x10086;
    private Button btn_check_time;
    private TextView tv_mymodule;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            switch (message.what) {
                case REFRESH_TV:
                    String str = (String) message.obj;
                    tv_mymodule.setText(str);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        btn_check_time = (Button) findViewById(R.id.btn_check_time);
        tv_mymodule = (TextView) findViewById(R.id.tv_mymodule);

        btn_check_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_check_time:
                checkTime();
                break;
        }
    }

    private void checkTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;//取得资源对象
                URLConnection uc;
                long ld;
                try {
                    url = new URL("http://www.baidu.com");
                    uc = url.openConnection();//生成连接对象
                    uc.connect(); //发出连接
                    ld = uc.getDate(); //取得网站日期时间
                    String date = getDate(ld);
                    Message message = new Message();
                    message.obj = date;
                    message.what = REFRESH_TV;
                    mHandler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private String getDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millis);
        return sdf.format(date);
    }
}
