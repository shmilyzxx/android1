package com.flag.myapplication.zuoye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView list =findViewById(R.id.list);

        List<String> aa=new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            aa.add(i+"");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        FruitAdapter fruitAdapter=new FruitAdapter(aa,this);
        list.setAdapter(fruitAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("zuoye","我是onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("zuoye","我是onDestroy");

    }



}
