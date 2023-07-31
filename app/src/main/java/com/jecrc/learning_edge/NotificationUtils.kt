package com.jecrc.learning_edge

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import java.io.File
import java.util.Locale

object NotificationUtils {
//    fun showNotification(context: Context, title: String, message: String) {
//        // Implement your code to show a notification here
//        // You can use NotificationManager to show a notification.
//        // For example:
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channelId = "channelId"
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            val channel =
//                android.app.NotificationChannel(
//                    channelId,
//                    "Channel Name",
//                    NotificationManager.IMPORTANCE_DEFAULT
//                )
//            notificationManager.createNotificationChannel(channel)
//        }
//        val notificationBuilder = NotificationCompat.Builder(context, channelId)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setSmallIcon(R.drawable.ic_notification)
//        notificationManager.notify(1, notificationBuilder.build())
//    }

    fun showNotification(context: Context, title: String, message: String, uri: Uri? = null) {
        // Create a PendingIntent to open the file when the notification is clicked
        val contentIntent = uri?.let {
            PendingIntent.getActivity(
                context,
                0,
                openFileIntent(context, it),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        // Create the notification
        val builder = NotificationCompat.Builder(context, MainActivity2.CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)

        // Show the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(MainActivity2.NOTIFICATION_ID, builder.build())
    }


    //    fun showNotification(context: Context, title: String, message: String, uri: Uri?) {
//        // Create a PendingIntent to open the file when the notification is clicked
//        val contentIntent = uri?.let {
//            PendingIntent.getActivity(
//                context,
//                0,
//                openFileIntent(context, it),
//                PendingIntent.FLAG_UPDATE_CURRENT // Use FLAG_UPDATE_CURRENT for proper handling
//            )
//        }
//
//        // Create the notification
//        val builder = NotificationCompat.Builder(context, MainActivity2.CHANNEL_ID)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentIntent(contentIntent)
//            .setAutoCancel(true)
//
//        // Show the notification
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(MainActivity2.NOTIFICATION_ID, builder.build())
//    }
    fun openFileIntent(context: Context, uri: Uri): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.setDataAndType(uri, getMimeType(context, uri))
        return intent
    }

    private fun getMimeType(context: Context, uri: Uri): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mimeResolver = MimeTypeMap.getSingleton()
            mimeResolver.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase(Locale.ROOT))
        }
    }
}
