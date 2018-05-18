package com.example.lenovo.qis_todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.qis_todolist.activity.OfflineTaskActivity;
import com.example.lenovo.qis_todolist.activity.OnlineTaskActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class MainActivity extends AppCompatActivity {

    Button btn_offline, btn_online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_offline = (Button) findViewById(R.id.btn_offline);
        btn_online = (Button) findViewById(R.id.btn_online);

        PushDownAnim.setPushDownAnimTo(btn_offline)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, OfflineTaskActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(btn_online)
                .setScale(PushDownAnim.MODE_SCALE, 0.89f)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, OnlineTaskActivity.class));
                    }
                });

    }

    @Override
    public void onBackPressed() {
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

}
