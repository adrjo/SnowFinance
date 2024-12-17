package com.github.adrjo.util;

import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

    public static Path getResourceAsPath(URL resource, String resourcePath) throws IOException, URISyntaxException {
        Path directory;
        URI uri = resource.toURI();
        if (uri.getScheme().equals("jar")) {
            FileSystem fs;
            try {
                fs = FileSystems.getFileSystem(uri);
            } catch (FileSystemNotFoundException e) {
                fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
            }
            directory = fs.getPath(resourcePath);
        } else {
            directory = Path.of(resource.toURI());
        }
        return directory;
    }

    /**
     * inspiration from
     * <a href="https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection">stackoverflow</a>
     */
    public static List<Class<?>> findClasses(String packageName) throws Exception {
        String path = packageName.replace('.', '/') + "/";
        URL packageURL = Thread.currentThread().getContextClassLoader().getResource(path);

        if (packageURL == null) return List.of();
        Path directory = getResourceAsPath(packageURL, path);

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

    public static String joinArgs(String[] args, int from) {
        return String.join(" ", Arrays.copyOfRange(args, from, args.length));
    }
}
