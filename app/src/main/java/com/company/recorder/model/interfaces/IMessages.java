package com.company.recorder.model.interfaces;


import android.text.SpannableString;
import android.widget.ImageView;


public interface IMessages {


    void toast(String message);

    void toastInternet();

    String timeConversion(long value);


    String calculateMin(long seconds);


}
