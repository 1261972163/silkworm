package com.jengine.java.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class RegexSample {

    public String replaceDotWithSlash(String inputStr) {
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
