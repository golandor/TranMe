package com.example.golan.train.FirebaseCloudMessaging;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyCloudMessagingService extends Service {
    public MyCloudMessagingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
