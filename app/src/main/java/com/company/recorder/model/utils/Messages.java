package com.company.recorder.model.utils;

import android.annotation.SuppressLint;

import android.content.Context;

import android.widget.Toast;


import com.company.recorder.R;
import com.company.recorder.model.interfaces.IMessages;
import java.util.concurrent.TimeUnit;


public class Messages implements IMessages {

    private final Context context;

    public Messages(Context context) {
        this.context = context;
    }


    @Override
    public void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    @SuppressLint("DefaultLocale")
    public String timeConversion(long value) {
        String audioTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            audioTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            audioTime = String.format("%02d:%02d", mns, scs);
        }
        return audioTime;
    }
    public String calculateMin(long seconds) {
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        return minute + ":" + second;

    }




    @Override
    public void toastInternet() {
        Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show();

    }



}

