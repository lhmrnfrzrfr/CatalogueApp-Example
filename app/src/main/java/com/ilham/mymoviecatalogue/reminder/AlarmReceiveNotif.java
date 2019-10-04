package com.ilham.mymoviecatalogue.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.ilham.mymoviecatalogue.R;
import com.ilham.mymoviecatalogue.activity.TabbedActivity;
import com.ilham.mymoviecatalogue.items.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

import static com.ilham.mymoviecatalogue.ApiUrl.API_KEY;

public class AlarmReceiveNotif extends BroadcastReceiver {
    public static final String TYPE_RELEASE_REMINDER = "ReleaseMovie";
    public static final String TYPE_DAILY_REMINDER = "DailyMovie";

    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_MESSAGE = "message";

    private ArrayList<Movie> listMovies = new ArrayList<>();

    private int notifId;

    private final static int ID_DAILY_REMINDER = 100;
    private final static int ID_RELEASE_REMINDER = 101;

    public AlarmReceiveNotif() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if(type.equalsIgnoreCase(TYPE_DAILY_REMINDER)) {
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            String title = context.getResources().getString(R.string.app_name);
            notifId = ID_DAILY_REMINDER;
            showAlarmNotification(context,title,message,notifId);
        }else{
            notifId = ID_RELEASE_REMINDER;
            checkNewReleaseMovies(new ReleaseMovieCallbacks() {
                @Override
                public void onSuccess(ArrayList<Movie> movies) {
                    listMovies = movies;
                    for(int i = 0; i < listMovies.size(); i++) {
                        showAlarmNotification(context,listMovies.get(i).getTitle(), listMovies.get(i).getTitle()+"today release!",notifId++);
                    }
                }

                @Override
                public void onFailure(boolean failure){
                    if(failure) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    private void checkNewReleaseMovies(final ReleaseMovieCallbacks callbacks){

        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todaydate = simpleDateFormat.format(date);

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte="+todaydate+"&primary_release_date.lte="+todaydate;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        Movie movie = new Movie(movies, "movie");
                        listItems.add(movie);
                    }
                    callbacks.onSuccess(listItems);
                    callbacks.onFailure(false);
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                callbacks.onFailure(true);
                callbacks.onSuccess(new ArrayList<Movie>());

            }
        });
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId){

        String CHANNEL_ID = "Channel_Id_1";
        String CHANNEL_NAME = "Channel_Alarm";

        Intent intent;
        intent = new Intent(context.getApplicationContext(), TabbedActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000,})
                .setSound(alarmSound)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentText(message)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();

        if(notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    public void setAlarmDaily(Context context, String type, boolean check){
        if (check) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiveNotif.class);
            intent.putExtra(EXTRA_MESSAGE, context.getResources().getString(R.string.check_daily));
            intent.putExtra(EXTRA_TYPE, type);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 3);
            calendar.set(Calendar.SECOND, 0);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0);
            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
            Toast.makeText(context, "daily reminder on", Toast.LENGTH_SHORT).show();
        }
        else
        {
            alarmOff(context, AlarmReceiveNotif.TYPE_DAILY_REMINDER);
        }
    }

    public void setAlarmRelease(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiveNotif.class);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, intent, 0);
        if(alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Release reminder on", Toast.LENGTH_SHORT).show();
    }

    public void alarmOff(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiveNotif.class);
        int requestCode = type.equalsIgnoreCase(TYPE_RELEASE_REMINDER) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }

        if(type.equalsIgnoreCase(TYPE_DAILY_REMINDER)){
            Toast.makeText(context, "Daily Reminder off", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Release Reminder off", Toast.LENGTH_LONG).show();
        }
    }


}
