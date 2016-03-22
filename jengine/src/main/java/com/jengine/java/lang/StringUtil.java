package com.jengine.java.lang;

/**
 * Created by nouuid on 2015/4/28.
 */
public class StringUtil {

    /**
     * 转化字符串为十六进制编码
     * @param s 字符串
     * @return
     */
    public static String hexCode(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 转化十六进制编码为字符串
     * @param s 十六进制编码字符串
     * @return
     */
    public static String hexDecode(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 使用gzip进行压缩
     */
//    public static String gzip(String primStr) {
//        if (primStr == null || primStr.length() == 0) {
//            return primStr;
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        GZIPOutputStream gzip = null;
//        try {
//            gzip = new GZIPOutputStream(out);
//            gzip.write(primStr.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (gzip != null) {
//                try {
//                    gzip.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
//    }

    /**
     * Description:使用gzip进行解压缩
     * @param compressedStr
     * @return
     */
//    public static String gunzip(String compressedStr) {
//        if (compressedStr == null) {
//            return null;
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ByteArrayInputStream in = null;
//        GZIPInputStream ginzip = null;
//        byte[] compressed = null;
//        String decompressed = null;
//        try {
//            compressed = new sun.misc.BASE64Decoder()
//                    .decodeBuffer(compressedStr);
//            in = new ByteArrayInputStream(compressed);
//            ginzip = new GZIPInputStream(in);
//
//            byte[] buffer = new byte[1024];
//            int offset = -1;
//            while ((offset = ginzip.read(buffer)) != -1) {
//                out.write(buffer, 0, offset);
//            }
//            decompressed = out.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ginzip != null) {
//                try {
//                    ginzip.close();
//                } catch (IOException e) {
//                }
//            }
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                }
//            }
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//
//        return decompressed;
//    }

    /**
     * 使用zip进行压缩
     *
     * @param str
     *            压缩前的文本
     * @return 返回压缩后的文本
     */
//    public static final String zip(String str) {
//        if (str == null)
//            return null;
//        byte[] compressed;
//        ByteArrayOutputStream out = null;
//        ZipOutputStream zout = null;
//        String compressedStr = null;
//        try {
//            out = new ByteArrayOutputStream();
//            zout = new ZipOutputStream(out);
//            zout.putNextEntry(new ZipEntry("0"));
//            zout.write(str.getBytes());
//            zout.closeEntry();
//            compressed = out.toByteArray();
//            compressedStr = new sun.misc.BASE64Encoder()
//                    .encodeBuffer(compressed);
//        } catch (IOException e) {
//            compressed = null;
//        } finally {
//            if (zout != null) {
//                try {
//                    zout.close();
//                } catch (IOException e) {
//                }
//            }
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        return compressedStr;
//    }

    /**
     * 使用zip进行解压缩
     * @param compressedStr 压缩后的文本
     * @return 解压后的字符串
     */
//    public static final String unzip(String compressedStr) {
//        if (compressedStr == null) {
//            return null;
//        }
//
//        ByteArrayOutputStream out = null;
//        ByteArrayInputStream in = null;
//        ZipInputStream zin = null;
//        String decompressed = null;
//        try {
//            byte[] compressed = new sun.misc.BASE64Decoder()
//                    .decodeBuffer(compressedStr);
//            out = new ByteArrayOutputStream();
//            in = new ByteArrayInputStream(compressed);
//            zin = new ZipInputStream(in);
//            zin.getNextEntry();
//            byte[] buffer = new byte[1024];
//            int offset = -1;
//            while ((offset = zin.read(buffer)) != -1) {
//                out.write(buffer, 0, offset);
//            }
//            decompressed = out.toString();
//        } catch (IOException e) {
//            decompressed = null;
//        } finally {
//            if (zin != null) {
//                try {
//                    zin.close();
//                } catch (IOException e) {
//                }
//            }
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                }
//            }
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        return decompressed;
//    }

}
