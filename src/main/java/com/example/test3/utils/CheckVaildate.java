package com.example.test3.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckVaildate {
    public static boolean checkVaild(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z]");
        Matcher m = p.matcher(s);
        boolean b = m.find();
        if (b == true)
            return false;
       return true;
    }
}
