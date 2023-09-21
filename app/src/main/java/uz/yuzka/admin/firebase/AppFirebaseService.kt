package uz.yuzka.admin.firebase

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import uz.yuzka.admin.R
import uz.yuzka.admin.pref.MyPref
import uz.yuzka.admin.ui.activity.MainActivity
import java.util.Random
import javax.inject.Inject

@AndroidEntryPoint
class AppFirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var pref: MyPref

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        pref.fcmToken = token
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val notificationData = NotificationData(
            title = remoteMessage.notification?.title,
            description = remoteMessage.notification?.body,
            action = remoteMessage.data["action"],
            objectId = remoteMessage.data["object_id"],
            userType = remoteMessage.data["recipient_user_type"]
        )
        createNotificationChannel()
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationData.title)
            .setContentText(notificationData.description)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(notificationData.description)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(generateRandom(), builder.build())
        }
    }

    private fun generateRandom(): Int {
        val random = Random()
        return random.nextInt(9999 - 1000) + 1000
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                importance
            ).apply {
                description = "PUSH Notification from Server"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "100k-admin-notification-channel"
        const val NOTIFICATION_CHANNEL_NAME = "100k-admin-notification-channel-name"

    }


}

data class NotificationData(
    val title: String?,
    val description: String?,
    val action: String?,
    val objectId: String?,
    val userType: String?
)
