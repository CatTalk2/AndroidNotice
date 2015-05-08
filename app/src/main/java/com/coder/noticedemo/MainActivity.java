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
     * ���Զ�Ӧ����
     */
    public void simpleToast(){
//        ��ʾһ���򵥵��ı���Ϣ
        Toast toast=Toast.makeText(MainActivity.this, "This is a simple Toast", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void customToast(){
        Toast toast=new Toast(MainActivity.this);
//        ����Toast����ʾλ��
        toast.setGravity(Gravity.BOTTOM, 0, 0);
//        ����Toast����ͼ�������������һ��ͼƬ
        LinearLayout layout=new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageView imageView=new ImageView(getApplicationContext());

        imageView.setImageResource(R.mipmap.touxiang);//����һ��ͼƬ
        layout.addView(imageView,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        toast.setView(layout);

//        ������ʾʱ��
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public void simpleNotification(){
//        ��ȡNotificationManagerʵ��
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        ����Notification.Builder ����
        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);

//        ����Notificationͼ��
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(myIcon);
//        ����Notification tickertext
        builder.setTicker("A new Message");
//        ����֪ͨ����Ŀ
        builder.setContentTitle("A new notification");
//        ����֪ͨ������
        builder.setContentText("This is content text");
        builder.setContentInfo("Info");
//        ����֪ͨ���Ա��Զ�ȡ��
        builder.setAutoCancel(true);
//        ����֪ͨ����ʾ��Notification��ʱ������
        builder.setWhen(System.currentTimeMillis());
//        ���������������ԣ�����֪ͨ��ʾ�����𶯡���Ļ�·�LED����˸
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));//��������һ�������ļ�Ϊ��ʾ��
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setLights(Color.RED,0,1);
//        ���ø�֪ͨ�����Ҫ������Intent,������Ҫע��PendingIntent���÷�,���췽���е��ĸ�����(context,int requestCode,Intent,int flags);
        Intent intent=new Intent(MainActivity.this,AnotherActivity.class);
        PendingIntent pi=PendingIntent.getActivity(MainActivity.this,0,intent,0);
        builder.setContentIntent(pi);

//        ʵ����Notification

        Notification notification=builder.build();//notify(int id,notification����);id������ʾÿ��notification
        manager.notify(1,notification);


    }
    public void customNotification(){
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);
        builder.setTicker("�������ڲ���");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
//        �����Զ���RemoteView
        RemoteViews view=new RemoteViews(getPackageName(),R.layout.remote_view);

        builder.setContent(view);
        PendingIntent pi=PendingIntent.getActivity(MainActivity.this,1,new Intent(MainActivity.this,AnotherActivity.class),0);
        builder.setContentIntent(pi);


        builder.setOngoing(true);
        manager.notify(2, builder.build());
    }
}
