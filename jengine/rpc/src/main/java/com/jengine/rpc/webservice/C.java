package com.jengine.rpc.webservice;

import javax.jws.WebService;

/**
 * content
 *
 * @author bl07637
 * @date 4/25/2017
 * @since 0.1.0
 */
@WebService(endpointInterface = "com.jengine.rpc.webservice.B")
public class C implements B {
    @Override
    public String sayB(String a) {
        return "B";
    }

    @Override
    public String sayAB(String a, String b) {
        return "AB";
    }

    @Override
    public String sayA(String b) {
        return "A";
    }
}
