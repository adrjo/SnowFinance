package com.github.adrjo.util;

import javafx.scene.control.Label;

import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Helper {

    public static final String DATE_AND_TIME = "yyyy-MM-dd.HH:mm";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_AND_TIME);

    public static final String DATABASE = "transactions.snow";

    public static Label getTitle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return label;
    }

    /**
     * inspiration from
     * <a href="https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection">stackoverflow</a>
     */
    public static List<Class<?>> findClasses(String packageName) throws Exception {
        String path = packageName.replace('.', '/') + "/";
        URL packageURL = Thread.currentThread().getContextClassLoader().getResource(path);

        if (packageURL == null) return List.of();
        URI uri = packageURL.toURI();
        Path directory;
        if (packageURL.toURI().getScheme().equals("jar")) {
            FileSystem fs;
            try {
                fs = FileSystems.getFileSystem(uri);
            } catch (FileSystemNotFoundException e) {
                fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
            }
            directory = fs.getPath(path);
        } else {
            directory = Path.of(packageURL.toURI());
        }


        List<Class<?>> classes = new ArrayList<>();
        Files.walk(directory)
                .map(filePath -> filePath.toAbsolutePath().toString().replaceAll("\\\\", "/"))
                .filter(name -> name.endsWith(".class"))
                .map(name -> name.split(path)[1])
                .map(name -> name.replaceAll("/", "."))
                .forEach(name -> {
                    name = name.split("\\.class")[0];
                    try {
                        classes.add(Class.forName(packageName + '.' + name));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        return classes;
    }

    public static int getInput(String lookingFor) {
        System.out.print(lookingFor + ": ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

}
