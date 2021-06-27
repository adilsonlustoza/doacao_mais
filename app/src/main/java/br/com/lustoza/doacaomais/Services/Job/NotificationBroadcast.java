package br.com.lustoza.doacaomais.Services.Job;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import br.com.lustoza.doacaomais.Helper.ConstantHelper;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

/**
 * Created by Adilson on 4/27/2017.
 */

public class NotificationBroadcast extends BroadcastReceiver {
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        TrackHelper.WriteInfo(this, "onReceive", String.format(" NotificationBroadcast - %s", new Date().toString()));

        if (context != null)
            ThrowAlarm(context);
    }

    private void ThrowAlarm(Context context) {
        try {
            TrackHelper.WriteInfo(this, "ThrowAlarm", String.format(" NotificationBroadcast - %s", new Date().toString()));

            Intent intent = new Intent(context, NotificationService.class);
            pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            boolean isActive = (pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE)) == null;

            if (!isActive) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 1);
                alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ConstantHelper.OneMinute * 5, pendingIntent);

            }

        } catch (Exception e) {
            TrackHelper.WriteInfo(this, "onPostExecute", new Date().toString());
            alarmManager.cancel(pendingIntent);
        }

    }

}
