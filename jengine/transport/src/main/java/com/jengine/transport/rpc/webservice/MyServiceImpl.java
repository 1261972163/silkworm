package com.jengine.transport.rpc.webservice;

/**
 * content
 *
 * @author bl07637
 * @date 9/12/2016
 * @since 0.1.0
 */
import javax.jws.WebService;

@WebService(endpointInterface = "com.jengine.transport.rpc.webservice.MyService")
public class MyServiceImpl implements MyService {

    @Override
    public int add(int a, int b) {
        System.out.println(a + "+" + b + "=" + (a + b) );
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        System.out.println(a + "-" + b + "=" + (a - b) );
        return a - b;
    }

}
