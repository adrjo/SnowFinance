package com.github.adrjo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Helper {

    public static final String DATE_AND_TIME = "yyyy-MM-dd.HH:mm";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_AND_TIME);

    public static final String DATABASE = "transactions.snow";
}
