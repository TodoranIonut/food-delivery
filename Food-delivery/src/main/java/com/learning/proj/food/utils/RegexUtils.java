package com.learning.proj.food.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * Extract first regex match
     * @param text for searching in
     * @param patternString regex pattern as string
     * @return
     */
    public static String extractFirstMatch(String text, String patternString){

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }
}
