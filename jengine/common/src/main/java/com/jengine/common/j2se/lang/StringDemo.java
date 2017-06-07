package com.jengine.common.j2se.lang;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/13.
 */
public class StringDemo {

    /**
     * compareTo
     */
    @Test
    public void strCompareTest() {
        String s1 = "20150102";
        String s2 = "20150102";
        Assert.assertEquals(0, s1.compareTo(s2));

        String s3 = "20150102";
        String s4 = "20150101";
        Assert.assertEquals(1, s3.compareTo(s4));

        String s5 = "20150101";
        String s6 = "20150102";
        Assert.assertEquals(-1, s5.compareTo(s6));
    }

    /**
     * matches
     */
    @Test
    public void strMatchesTest() {
        String regex = "xingng-[a-z]-\\d{8}";
        String s1 = "xingng-a-20160320";
        Assert.assertTrue(s1.matches(regex));
        String s2 = "xingng-a-201603202";
        Assert.assertFalse(s2.matches(regex));
    }

    /**
     * split
     */
    @Test
    public void split() {
        String s = "1/2/1";
        String[] splits = s.split("/");
        if (splits==null) {
            System.out.println("splits is null");
        } else {
            System.out.println("splits.length=" + splits.length);
        }
        for (String split : splits) {
            System.out.println(split);
        }
    }

    @Test
    public void sub() {
        String regex = "/bingo[a-z0-9A-Z/]*";
//        String home = "/bingo/x/ /x/dd/ddd/ /d /////";
        String home = "/bingo/////";
        home = home.trim();

        System.out.println(home.matches(regex));


//        if (home.contains("/")) {
//            String[] splits = home.split("/");
//            StringBuilder newHome = new StringBuilder();
//            for (String split : splits) {
//                if (StringUtils.isNotBlank(split)) {
//                    newHome.append("/" + split);
//                }
//            }
//            home = newHome.toString();
//        }
//
//        home = home.trim();
//        if (!home.startsWith("/bingo")) {
//            home = "/bingo" + home;
//        }
//        while (home.endsWith("/")) {
//            int endIndex = 0;
//            for (int i = home.length() - 1; i >= 0; i--) {
//                if (!"/".equals(home.charAt(i)+"")) {
//                    endIndex = i + 1;
//                    break;
//                }
//            }
//            home = home.substring(0, endIndex);
//            home = home.trim();
//        }
//        System.out.println(home);

    }

}
