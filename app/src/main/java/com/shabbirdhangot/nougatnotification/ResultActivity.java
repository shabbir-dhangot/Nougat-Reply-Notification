package com.shabbirdhangot.nougatnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    Context context;
    NotificationManager notificationManager;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        context = this;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        resultText = (TextView) findViewById(R.id.resultText);
        CharSequence messageToBeSend = getReplyMessage(getIntent());
        if(messageToBeSend != null)
            resultText.setText(messageToBeSend);

        //Simulate Network Operation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateNotification(true);
            }
        },2000);

    }

    /**
     * @param autoDismiss to dismiss notification after message sent.
     */
    private void updateNotification(boolean autoDismiss) {
        Notification repliedNotification =
                new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("Message sent")
                        .build();
        notificationManager.notify(Constants.NOTIFICATION_ID, repliedNotification);

        if(autoDismiss) {
            //you can dissmiss after
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    notificationManager.cancel(Constants.NOTIFICATION_ID);
                }
            }, 1000);
        }
    }

    /**
     * @param intent Obtain the intent that started this activity by calling Activity.getIntent() and pass it into this method to get the associated string.
     * @return message from RemoteInput
     */
    private CharSequence getReplyMessage(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(Constants.REPLY_KEY);
        }
        return null;
    }
}
