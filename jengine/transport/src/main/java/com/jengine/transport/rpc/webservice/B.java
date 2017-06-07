package com.jengine.transport.rpc.webservice;

import javax.jws.WebService;

/**
 * @author bl07637
 * @date 4/25/2017
 */
@WebService
public interface B extends A {
    String sayB(String b);
    String sayAB(String a, String b);
}
