package me.ghit.rave.utils;

public class TextUtils {
    // Example: "this is a sentence" -> "This is a sentence"
    public static String capitalizeFirst(String raw) {
        if(raw == null || raw.isEmpty()) {
            return raw;
        }

        return raw.substring(0, 1).toUpperCase() + raw.substring(1);
    }
}
