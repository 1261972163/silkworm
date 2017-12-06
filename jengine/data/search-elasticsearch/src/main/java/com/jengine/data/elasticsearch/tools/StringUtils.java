package com.jengine.data.elasticsearch.tools;

/**
 * @author nouuid
 * @date 8/4/2016
 * @description
 */
public class StringUtils {
  public static boolean isBlank(String s) {
    if (s != null && s.trim().length() > 0) {
      return false;
    }
    return true;
  }

  public static boolean isNotBlank(String s) {
    return !isBlank(s);
  }

  public static String camelToSplitName(String camelName, String split) {
    if (camelName == null || camelName.length() == 0) {
      return camelName;
    }
    StringBuilder buf = null;
    for (int i = 0; i < camelName.length(); i++) {
      char ch = camelName.charAt(i);
      if (ch >= 'A' && ch <= 'Z') {
        if (buf == null) {
          buf = new StringBuilder();
          if (i > 0) {
            buf.append(camelName.substring(0, i));
          }
        }
        if (i > 0) {
          buf.append(split);
        }
        buf.append(Character.toLowerCase(ch));
      } else if (buf != null) {
        buf.append(ch);
      }
    }
    return buf == null ? camelName : buf.toString();
  }

  public static String lowerCase(String str) {
    if (str == null) {
      return null;
    }
    return str.toLowerCase();
  }

  public static String upperCase(String str) {
    if (str == null) {
      return null;
    }
    return str.toUpperCase();
  }
}
