package cis436mn.project3;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.id.message;
import static cis436mn.project3.R.id.messageIn;
import static cis436mn.project3.R.id.text;
import static cis436mn.project3.R.id.textView;

public class MyService extends Service {

    CountDownTimer countDownTime = null;

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.clock)
            .setContentTitle("")
            .setContentText("");
    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    public int NotifID = 001;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Countdown has been started", Toast.LENGTH_LONG).show();

        int seconds = intent.getExtras().getInt("time");
        final String userMessage = intent.getExtras().getString("getMessageText");
        final int highNotifLength = intent.getExtras().getInt("highSpin");

        final NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);  //initialize notification service


        countDownTime = new CountDownTimer(seconds * 1000, 1000){       //under these conditions, the notifications get pushed out
            @Override
            public void onTick(long millis){

                if((millis / 1000) == 90 && highNotifLength == 6)
                {
                    notifyBack(userMessage, 90, notifManager);
                }
                else if((millis / 1000) == 60 && highNotifLength >= 5)
                {
                    notifyBack(userMessage, 60, notifManager);
                }
                else if((millis / 1000) == 30 && highNotifLength >= 4)
                {
                    notifyBack(userMessage, 30, notifManager);
                }
                else if((millis / 1000) == 20 && highNotifLength >= 3)
                {
                    notifyBack(userMessage, 20, notifManager);
                }
                else if((millis / 1000) == 10 && highNotifLength >= 2)
                {
                    notifyBack(userMessage, 10, notifManager);
                }
                else if((millis / 1000) == 5 && highNotifLength >= 1)
                {
                    notifyBack(userMessage, 5, notifManager);
                }
                else if((millis / 1000) == 1 && highNotifLength >= 0)
                {
                    notifyBack(userMessage, 1, notifManager);
                }
            }
            @Override
            public void onFinish(){

                //NotifID++; // add this to make it a separate notification

                builder.setContentTitle("Timer Finished!");
                builder.setContentText("Time for: " + userMessage);
                builder.setSound(sound);
                notifManager.notify(NotifID, builder.build());
                onDestroy();
            }
        }.start();
        return START_REDELIVER_INTENT; //services will run even after closing recents
    }

    private void notifyBack(String message, int seconds, NotificationManager notificationManager)   //sends notifcation template out, function takes in data
    {
        builder.setContentTitle("Timer Notification");
        builder.setContentText(seconds + " seconds until " + message);
        builder.setSound(sound);
        notificationManager.notify(NotifID, builder.build());
    }

    @Override
    public void  onDestroy(){
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();  //ends service
        countDownTime.cancel();
        super.onDestroy();
    }
}
