package anup.birthdayreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


public class ReminderReciever extends BroadcastReceiver {
    DatabaseHelper dbHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Reminder Notification")
                        .setContentText("hello");

        NotificationManager  mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(0, mBuilder.build());

        dbHelper = new DatabaseHelper(context);
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String smsBody = "This is an SMS!";

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", phoneNumber);
        smsIntent.putExtra("sms_body", smsBody);
        context.startActivity(smsIntent);









    }
}
