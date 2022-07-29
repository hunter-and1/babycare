package com.wicoding.injaz.babycare;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HunTerAnD1 on 17/05/2017.
 */
public class DataUser {
    private String _NameBaby;
    private String _DateOfBirth;
    private String _LangApp = "fr";
    private Bitmap _Img;
    //_ImageBaby
    public DataUser() {}

    public Bitmap get_Img() {
        return _Img;
    }

    public void set_Img(Bitmap _Img) {
        this._Img = _Img;
    }


    public String get_NameBaby() {
        return _NameBaby;
    }

    public void set_NameBaby(String _NameBaby) {
        this._NameBaby = _NameBaby;
    }

    public String get_DateOfBirth() {
        return _DateOfBirth;
    }

    public void set_DateOfBirth(String _DateOfBirth) {
        this._DateOfBirth = _DateOfBirth;
    }

    public String get_LangApp() {
        return _LangApp;
    }

    public void set_LangApp(String _LangApp) {
        this._LangApp = _LangApp;
    }

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("_NameBaby", get_NameBaby());
            jsonObject.put("_DateOfBirth", get_DateOfBirth());
            jsonObject.put("_LangApp", get_LangApp());
            jsonObject.put("_Img", GlobalApp.getStringFromBitmap(get_Img()));
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }
}
