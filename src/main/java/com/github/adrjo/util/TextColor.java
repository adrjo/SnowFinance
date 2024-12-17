package com.github.adrjo.util;

public class TextColor {

    private static final char ESC = '\u001B';
    public static final String GREEN = ESC + "[32m";
    public static final String RESET = ESC + "[0m";
    public static final String WHITE = ESC + "[37m";
    public static final String RED = ESC + "[31m";
    public static final String BOLD = ESC + "[1m";

    public static String getFormatForColor(int b) {
        return ESC + "[38:5:" + b + "m";
    }
}
