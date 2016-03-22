package com.jengine.java.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nouuid on 2015/5/13.
 */
public class StringServiceImpl implements StringService {
    @Override
    public String replace(String inputStr) {
        String res = inputStr;
        Pattern p = null;
        Matcher m = null;

        while(res.contains(".")) {
            p = Pattern.compile("\\.");
            m = p.matcher(res);
            res = m.replaceFirst("\\/");
        }

        return res;
    }
}
