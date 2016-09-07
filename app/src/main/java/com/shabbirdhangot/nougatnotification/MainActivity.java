package com.shabbirdhangot.nougatnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext=this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
    }

    private void showNotification() {
        String replyLabel = getString(R.string.reply);
        RemoteInput remoteInput = new RemoteInput.Builder(Constants.REPLY_KEY)
                .setLabel(replyLabel)
                .build();

        Intent notificationIntent = null;
        notificationIntent = new Intent(this, ResultActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification.Action action = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            action = new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_reply),
                            getString(R.string.reply), contentIntent)
                            .addRemoteInput(remoteInput)
                            .build();
        }else{
            action = new Notification.Action.Builder(R.drawable.ic_reply,
                    getString(R.string.reply), contentIntent)
                    .addRemoteInput(remoteInput)
                    .build();
        }

        Notification newMessageNotification =
                new Notification.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.username))
                        .setContentText(getString(R.string.content))
                        .addAction(action).build();

        NotificationManager notificationManager =(NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID, newMessageNotification);
        finish();
    }
}
