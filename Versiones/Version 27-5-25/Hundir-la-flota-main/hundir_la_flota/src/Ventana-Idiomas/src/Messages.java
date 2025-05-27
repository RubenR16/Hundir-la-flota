package com.ejemplo;

import java.util.*;

public class Messages {
    private static Locale currentLocale = new Locale("es", "ES");
    private static ResourceBundle labels;
    private static ResourceBundle errors;
    private static ResourceBundle warnings;
    private static ResourceBundle info;

    static {
        loadLocale(currentLocale);
    }

    public static void loadLocale(Locale locale) {
        currentLocale = locale;
        String folder = locale.getLanguage().equals("en") ? "ENG" : "CAS";
        labels = ResourceBundle.getBundle(folder+"/labels", currentLocale);
        errors = ResourceBundle.getBundle(folder+"/errors", currentLocale);
        warnings = ResourceBundle.getBundle(folder+"/warnings", currentLocale);
        info = ResourceBundle.getBundle(folder+"/info", currentLocale);
    }

    public static ResourceBundle labels() { return labels; }
    public static ResourceBundle errors() { return errors; }
    public static ResourceBundle warnings() { return warnings; }
    public static ResourceBundle info() { return info; }
}