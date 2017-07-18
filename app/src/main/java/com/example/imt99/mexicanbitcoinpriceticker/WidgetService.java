package com.example.imt99.mexicanbitcoinpriceticker;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Markintoch on 14/07/2017.
 */

public class WidgetService extends IntentService {
    Timer loopTiempo = new Timer();

    public WidgetService() {
        super("Widget Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        loopTiempo.schedule(new LoopService(),0,60000);
    }


    class LoopService extends TimerTask{
        public void run(){
            new BitsoJSON(MainActivity.getAppContext()).execute();
        }
    }
}
