package br.com.lustoza.doacaomais.Helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import br.com.lustoza.doacaomais.MainActivity;
import br.com.lustoza.doacaomais.R;

/**
 * Created by Adilson on 15/07/2017.
 */

public class NotificationHelper {

    static String _notificationID = "001";
    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void ShowNotification(String tituto, String descricao, String[] conteudo) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, _notificationID);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(descricao);
        builder.setContentTitle(tituto);
        builder.setContentText(descricao);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.information_16);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder.setFullScreenIntent(pendingIntent, false);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();

        for (String s : conteudo) style.addLine(s);

        builder.setStyle(style);

        Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(Integer.parseInt(_notificationID), notification);

        try {

            String prefRingstone = PrefHelper.getString(context, PrefHelper.PreferenciaRingstone);
            if (prefRingstone == null) {
                Uri uri = Uri.parse(prefRingstone);
                Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
                ringtone.play();
                ringtone.stop();
            }

        } catch (Exception e) {
            TrackHelper.WriteError(this, "ShowNotification", e.getMessage());

        }

    }

}
