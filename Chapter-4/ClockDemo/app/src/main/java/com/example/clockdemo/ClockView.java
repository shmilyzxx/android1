package com.example.clockdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class ClockView extends View {

    private int DEFAULT_BORDER_WIDTH = 4;
    private int DEFAULT_LONG_DEGREE_LENGTH = 25;
    private int DEFAULT_SHORT_DEGREE_LENGTH = 15;
    private int DEFAULT_POINT_BACK_LENGTH = 3;
    private int hourPointerLength = 120;
    private int minutePointerLength = 160;
    private int secondPointerLength = 200;
    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public void StartClock(){
        isRun = true;
        timeThread.start();
        Log.d("kay", "ClockView: ");
    }

    public void StopClock(){
        if(timeThread != null){
            isRun = false;
            timeThread.stop();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.d("kay", "onDraw: ");

        //画外圆
        float borderWidth = DEFAULT_BORDER_WIDTH;
        int r = Math.min(getHeight() / 2, getWidth() /2 ) - (int)borderWidth;
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(borderWidth);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r , paintCircle);

        //画刻度线
        float degreeLength = 0f;
        Paint paintDegree = new Paint();
        paintDegree.setAntiAlias(true);
        for(int i=0;i<60;i++) {
            if (i % 5 == 0) {
                paintDegree.setStrokeWidth(6);
                degreeLength = DEFAULT_LONG_DEGREE_LENGTH;
            } else {
                paintDegree.setStrokeWidth(3);
                degreeLength = DEFAULT_SHORT_DEGREE_LENGTH;
            }
            Log.d("kay", "onDraw: " + getWidth() / 2 + "  " + getHeight() / 2);
            canvas.drawLine(getWidth() / 2, Math.abs(getWidth() / 2 - getHeight() / 2) + DEFAULT_BORDER_WIDTH, getWidth() / 2, Math.abs(getWidth() / 2 - getHeight() / 2) + DEFAULT_BORDER_WIDTH + degreeLength , paintDegree);
            canvas.rotate(360 / 60, getWidth() / 2, getHeight() / 2);
        }

        //刻度数字
        int degressNumberSize = 30;
        canvas.translate(getWidth() / 2, getHeight() / 2);
        Paint paintDegreeNumber = new Paint();
        paintDegreeNumber.setTextAlign(Paint.Align.CENTER);
        paintDegreeNumber.setTextSize(degressNumberSize);
        paintDegreeNumber.setFakeBoldText(true);
        for(int i=0;i<12;i++){
            float[] temp = calculatePoint((i+1)*30, r - DEFAULT_LONG_DEGREE_LENGTH - degressNumberSize/2 - 15);
            canvas.drawText((i+1)+"", temp[2], temp[3] + degressNumberSize/2-6, paintDegreeNumber);
        }

        //画指针
        Paint paintHour = new Paint();
        paintHour.setAntiAlias(true);
        paintHour.setStrokeWidth(15);
        Paint paintMinute = new Paint();
        paintMinute.setAntiAlias(true);
        paintMinute.setStrokeWidth(10);
        Paint paintSecond = new Paint();
        paintSecond.setAntiAlias(true);
        paintSecond.setStrokeWidth(5);
        Calendar now = Calendar.getInstance();
        float[] hourPoints = calculatePoint(now.get(Calendar.HOUR_OF_DAY)%12/12f*360, hourPointerLength);
        canvas.drawLine(hourPoints[0], hourPoints[1], hourPoints[2], hourPoints[3], paintHour);
        float[] minutePoints = calculatePoint(now.get(Calendar.MINUTE)/60f*360, minutePointerLength);
        canvas.drawLine(minutePoints[0], minutePoints[1], minutePoints[2], minutePoints[3], paintMinute);
        float[] secondPoints = calculatePoint(now.get(Calendar.SECOND)/60f*360, secondPointerLength);
        canvas.drawLine(secondPoints[0], secondPoints[1], secondPoints[2], secondPoints[3], paintSecond);

        //画圆心
        Paint paintCenter = new Paint();
        paintCenter.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, 2, paintCenter);
    }

    private boolean isRun = true;;
    private Thread timeThread = new Thread() {
        @Override
        public void run() {
            try {
                while(isRun){
                    updateHandler.sendEmptyMessage(0);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("kay", "handleMessage: ");
            invalidate();
        }
    };

    /**
     * 根据角度和长度计算线段的起点和终点的坐标
     * @param angle
     * @param length
     * @return
     */
    private float[] calculatePoint(float angle, float length){
        float[] points = new float[4];
        if(angle <= 90f){
            points[0] = -(float) Math.sin(angle*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[1] = (float) Math.cos(angle*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[2] = (float) Math.sin(angle*Math.PI/180) * length;
            points[3] = -(float) Math.cos(angle*Math.PI/180) * length;
        }else if(angle <= 180f){
            points[0] = -(float) Math.cos((angle-90)*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[1] = -(float) Math.sin((angle-90)*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[2] = (float) Math.cos((angle-90)*Math.PI/180) * length;
            points[3] = (float) Math.sin((angle-90)*Math.PI/180) * length;
        }else if(angle <= 270f){
            points[0] = (float) Math.sin((angle-180)*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[1] = -(float) Math.cos((angle-180)*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[2] = -(float) Math.sin((angle-180)*Math.PI/180) * length;
            points[3] = (float) Math.cos((angle-180)*Math.PI/180) * length;
        }else if(angle <= 360f){
            points[0] = (float) Math.cos((angle-270)*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[1] = (float) Math.sin((angle-270)*Math.PI/180) * DEFAULT_POINT_BACK_LENGTH;
            points[2] = -(float) Math.cos((angle-270)*Math.PI/180) * length;
            points[3] = -(float) Math.sin((angle-270)*Math.PI/180) * length;
        }
        return points;
    }
}
