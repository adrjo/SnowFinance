package com.github.adrjo;

import javafx.scene.control.Label;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Helper {

    public static final String DATE_AND_TIME = "yyyy-MM-dd.HH:mm";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_AND_TIME);

    public static final String DATABASE = "transactions.snow";

    public static Label getTitle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return label;
    }
}
