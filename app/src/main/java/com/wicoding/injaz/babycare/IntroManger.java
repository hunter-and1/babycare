package com.wicoding.injaz.babycare;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HunTerAnD1 on 20/03/2017.
 */

public class IntroManger {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    //mode
    int PRIVATE_MODE = 0;

    // file name
    private static final String PREF_NAME = "BabycareLaunch";
    private static final String IS_FIRST_TIME = "IsFirstTime";

    public IntroManger(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME, true);
    }
}
