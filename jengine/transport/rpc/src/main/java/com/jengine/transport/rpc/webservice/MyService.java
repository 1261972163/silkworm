package com.jengine.transport.rpc.webservice;

import javax.jws.WebService;

/**
 * content
 *
 * @author nouuid
 * @date 9/12/2016
 * @since 0.1.0
 */
@WebService
public interface MyService {
    public int add (int a, int b);

    public int minus (int a, int b);
}
