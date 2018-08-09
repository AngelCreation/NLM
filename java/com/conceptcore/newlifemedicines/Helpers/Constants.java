package com.conceptcore.newlifemedicines.Helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by SVF 15213 on 02-06-2018.
 */

public class Constants {
    public static final String HOST_NAME = "http://newlifemedicines.com/";
    public static final String SERVICE_URL = HOST_NAME + "api/";

    public static final String USER_DETAILS = "user_details";
    public static final SimpleDateFormat sdfDatetimeMobile = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

    public static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    public static final DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yy");
    public static final DateFormat dateFormatOrder = new SimpleDateFormat("EEE, d MMM yy hh:mma");
}
