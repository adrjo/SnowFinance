package com.github.adrjo;

import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static final String DATE_AND_TIME = "yyyy-MM-dd.HH:mm";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_AND_TIME);

    public static final String DATABASE = "transactions.snow";

    public static Label getTitle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return label;
    }

    public static List<Class<?>> findClasses(String packageName) throws Exception {
        String path = packageName.replace('.', '/');
        URL packageURL = Thread.currentThread().getContextClassLoader().getResource(path);

        if (packageURL == null) return List.of();

        File directory = new File(packageURL.toURI());
        List<Class<?>> classes = new ArrayList<>();

        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(packageName + '.' + className));
            }
        }

        return classes;
    }
}
