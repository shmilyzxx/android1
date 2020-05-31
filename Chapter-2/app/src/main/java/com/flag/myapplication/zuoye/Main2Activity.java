package com.flag.myapplication.zuoye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        int index=getIntent().getIntExtra("index",0);
     TextView tt= findViewById(R.id.tt);
        tt.setText("我是第"+index);
    }
}
