package com.hybrid.sample;

/**
 * Created by 맞춤팀 on 2018-03-21.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.net.URL;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    Bitmap bigPicture;
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //추가한것
        sendNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("imgurllink"));
        Log.d(TAG, "imgurl: " + remoteMessage.getData().get("imgurllink"));

    }

    private void sendNotification(String messageBody, String myimgurl) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //이미지 온라인 링크를 가져와 비트맵으로 바꾼다.
        try {
            URL url = new URL(myimgurl);
            bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
               .setSmallIcon(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ? R.drawable.ic_stat_name : R.mipmap.ic_launcher)
                .setColor(0xffffaec9)
                .setContentTitle("생각 전달")
           //     .setContentText(messageBody)
            //    .setAutoCancel(true)
                .setContentText("알림탭을 아래로 천천히 드래그 하세요.")
                .setAutoCancel(true)
                .setOngoing(true)
                .setSound(defaultSoundUri)
                //이미지를 보내는 스타일 사용하기
                .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bigPicture)
                .setBigContentTitle("FCM Push Big Text Title")
                .setSummaryText(messageBody))

                .setContentIntent(pendingIntent);



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}