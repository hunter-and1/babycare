package com.wicoding.injaz.babycare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by HunTerAnD1 on 17/05/2017.
 */

public class GlobalApp {

    public static final String MyPREFERENCES = "MyPrefsLogin";
    public static final String MyKeyPlacesAPI = "AIzaSyDYszlij8rt0gl86Q-eUfPQWBHJfYP2MMc";
    public static final DataUser dataUser = new DataUser();
    private static SharedPreferences sharedpreferences = null;
    public static void initializeSharedPreferences(Context c){
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public  static SharedPreferences getSharedPreferences(){
        return sharedpreferences;
    }

    public static SharedPreferences setLoginSharedPreferences(Context c)
    {
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("login", true);
        editor.putString("user",dataUser.toJSON());
        editor.commit();
        return sharedpreferences;
    }

    public static SharedPreferences setLogOutSharedPreferences(Context c)
    {
        sharedpreferences = c.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        return sharedpreferences;
    }

    public static void setDataUser() {
        if(getSharedPreferences().getBoolean("login",false))
        {
            try{
                JSONObject obj = new JSONObject(getSharedPreferences().getString("user",null));
                dataUser.set_NameBaby(obj.getString("_NameBaby"));
                dataUser.set_DateOfBirth(obj.getString("_DateOfBirth"));
                dataUser.set_LangApp(obj.getString("_LangApp"));
                dataUser.set_Img(getBitmapFromString(obj.getString("_Img")));
            }catch (Exception e){}
        }
    }

    public static String addZero(int number,int maxNumber)
    {
        String returnFull = number+"";
        if((number+"").length()<maxNumber)
        {
            for (int i = 0;i<maxNumber-(number+"").length();i++)
                returnFull = "0"+number;
        }
        return returnFull;
    }

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap getBitmapFromString(String jsonString) {
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    //DAta TExt

    public static  int[] listviewImage = new int[]{
            R.drawable.quicktipicon
    };
    //public static  int[] listviewImageHeader = new int[]{};
    // Array of strings for ListView Title
    public static int[] listviewTitle = new int[]{
            R.string.astuces_1,
            R.string.astuces_2,
            R.string.astuces_3,
            R.string.astuces_4,
            R.string.astuces_5,
    };

    public static int[] listviewDescription = new int[]{
            R.string.desc_astuces_1,
            R.string.desc_astuces_2,
            R.string.desc_astuces_3,
            R.string.desc_astuces_4,
            R.string.desc_astuces_5,
    };

    public static void setLocale(Context c,String lang) {

        Locale myLocale = new Locale(lang);
        DisplayMetrics dm = c.getResources().getDisplayMetrics();
        Configuration conf = c.getResources().getConfiguration();
        conf.locale = myLocale;
        c.getResources().updateConfiguration(conf, dm);
    }



}
