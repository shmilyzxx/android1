package com.example.clockdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ClockView clockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockView = (ClockView)findViewById(R.id.clock);
        clockView.StartClock();
    }


    @Override
    protected void onStop() {
        super.onStop();
        clockView.StopClock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
