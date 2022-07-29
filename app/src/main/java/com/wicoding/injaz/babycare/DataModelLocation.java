package com.wicoding.injaz.babycare;

/**
 * Created by HunTerAnD1 on 18/05/2017.
 */

public class DataModelLocation {
    private int iType;
    private String Title;
    private String SubTitle;
    private String LanLog;

    public DataModelLocation(int iType, String title, String subTitle, String lanLog) {
        this.iType = iType;
        Title = title;
        SubTitle = subTitle;
        LanLog = lanLog;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public int getiType() {
        return iType;
    }

    public void setiType(int iType) {
        this.iType = iType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLanLog() {
        return LanLog;
    }

    public void setLanLog(String lanLog) {
        LanLog = lanLog;
    }
}
