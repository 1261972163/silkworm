package  com.jengine.common.javacommon.utils;

import java.io.UnsupportedEncodingException;

/**
 * content
 *
 * @author nouuid
 * @date 6/5/2017
 * @since 0.1.0
 */
public class StringUtils {

  public static boolean isBlank(String s) {
    if (s == null || "".equals(s.trim())) {
      return true;
    }
    return false;
  }

  public static boolean isNotBlank(String s) {
    return !isBlank(s);
  }

  /**
   * String.subString has wrong in dealing with chinese
   */
  public static String substring(String src, int start_idx, int end_idx) {
    byte[] b = new byte[0];
    try {
      b = src.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = start_idx; i <= end_idx; i++) {
      stringBuilder.append((char) b[i]);
    }
    return stringBuilder.toString();
  }
}
