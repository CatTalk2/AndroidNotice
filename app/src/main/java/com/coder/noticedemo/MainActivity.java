package com.coder.noticedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button simpleToast,customToast;
    private Button simpleNotification,customNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleToast= (Button) findViewById(R.id.simple_toast);
        customToast= (Button) findViewById(R.id.custom_toast);
        simpleNotification= (Button) findViewById(R.id.simple_notification);
        customNotification= (Button) findViewById(R.id.custom_notification);

        simpleToast.setOnClickListener(this);
        customToast.setOnClickListener(this);
        simpleNotification.setOnClickListener(this);
        customNotification.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simple_toast:
                simpleToast();
                break;
            case R.id.custom_toast:
                customToast();
                break;
            case R.id.simple_notification:
                simpleNotification();
                break;
            case R.id.custom_notification:
                customNotification();
                break;
        }
    }

    /**
     * 各自对应函数
     */
    public void simpleToast(){
//        显示一个简单的文本信息
        Toast toast=Toast.makeText(MainActivity.this, "This is a simple Toast", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void customToast(){
        Toast toast=new Toast(MainActivity.this);
//        设置Toast的显示位置
        toast.setGravity(Gravity.BOTTOM, 0, 0);
//        设置Toast的视图，这里我们添加一张图片
        LinearLayout layout=new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageView imageView=new ImageView(getApplicationContext());

        imageView.setImageResource(R.mipmap.touxiang);//设置一张图片
        layout.addView(imageView,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        toast.setView(layout);

//        设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void simpleNotification(){
//        获取NotificationManager实例
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        构造Notification.Builder 对象
        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);

//        设置Notification图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(myIcon);
//        设置Notification tickertext
        builder.setTicker("A new Message");
//        设置通知的题目
        builder.setContentTitle("A new notification");
//        设置通知的内容
        builder.setContentText("This is content text");
        builder.setContentInfo("Info");
//        设置通知可以被自动取消
        builder.setAutoCancel(true);
//        设置通知栏显示的Notification按时间排序
        builder.setWhen(System.currentTimeMillis());
//        设置其他物理属性，包括通知提示音、震动、屏幕下方LED灯闪烁
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));//这里设置一个本地文件为提示音
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setLights(Color.RED,0,1);
//        设置该通知点击后将要启动的Intent,这里需要注意PendingIntent的用法,构造方法中的四个参数(context,int requestCode,Intent,int flags);
        Intent intent=new Intent(MainActivity.this,AnotherActivity.class);
        PendingIntent pi=PendingIntent.getActivity(MainActivity.this,0,intent,0);
        builder.setContentIntent(pi);

//        实例化Notification

        Notification notification=builder.build();//notify(int id,notification对象);id用来标示每个notification
        manager.notify(1,notification);


    }
    public void customNotification(){
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);
        builder.setTicker("音乐正在播放");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
//        设置自定义RemoteView
        RemoteViews view=new RemoteViews(getPackageName(),R.layout.remote_view);

        builder.setContent(view);
        PendingIntent pi=PendingIntent.getActivity(MainActivity.this,1,new Intent(MainActivity.this,AnotherActivity.class),0);
        builder.setContentIntent(pi);


        builder.setOngoing(true);
        manager.notify(2, builder.build());
    }
}
