package com.example.lenovo.qis_todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.qis_todolist.activity.OfflineTaskActivity;
import com.example.lenovo.qis_todolist.activity.OnlineTaskActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_offline, btn_online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_offline = (Button) findViewById(R.id.btn_offline);
        btn_online = (Button) findViewById(R.id.btn_online);

        btn_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OfflineTaskActivity.class));
            }
        });

        btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OnlineTaskActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

}
