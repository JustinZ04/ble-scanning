package com.example.btscanning;

import android.os.ParcelUuid;

public class Constants
{
    public static final ParcelUuid UUID = ParcelUuid
            .fromString("cac426a3-344f-45c8-8819-ebcfe81e4b23");
    public static final String URL = "https://ble-attendance.herokuapp.com";
    public static final String LOGIN = "/api/professors/login/" + APIKeys.apiKey;
    public static final String Classes = "/api/classes/";
    public static boolean LOGGED_IN = false;
}
