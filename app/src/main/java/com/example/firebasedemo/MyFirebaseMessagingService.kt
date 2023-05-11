package com.example.firebasedemo

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.RemoteMessage.Notification

const val notification_channel_id = "notification_id";
const val notification_name = "firebasedemo"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("Practice", "RECEIVED MESSAGE: " + (message.notification?.body ?: "null"));

        if (message.notification != null) {
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }


    //remote view
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView =
            RemoteViews("com.example.firebasedemo", R.layout.custom_notification_layout);
        remoteView.setTextViewText(R.id.notificationTitle, title);
        remoteView.setTextViewText(R.id.notificationMessage, message);
        return remoteView;
    }

    //generate notification
    @SuppressLint("UnspecifiedImmutableFlag")
    fun generateNotification(title: String, message: String) {


        //create intent where we want to go after click notification
        val intent = Intent(this, LoginScreen::class.java);
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        //pending intent execute in future (flag top means only only one time this intent used after it will be destroy by android)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        //create notification builder and provide some useful info
        val builder = NotificationCompat.Builder(this, notification_channel_id);
        builder.setSmallIcon(R.drawable.ic_complete_logo);
        builder.setAutoCancel(true);
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000));
        builder.setContentIntent(pendingIntent);
        builder.setOnlyAlertOnce(true)
        builder.setContent(getRemoteView(title, message));


        //create notification manager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val notificationChannel = NotificationChannel(
            notification_channel_id, notification_name, NotificationManager.IMPORTANCE_HIGH
        );


        //create notification channel using notification manager
        notificationManager.createNotificationChannel(notificationChannel);

        //display notification
        notificationManager.notify(0, builder.build())
    }
    //attach created notification with custom layout
    //show the notification
}