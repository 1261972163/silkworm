package com.jengine.feature.webservice;

import javax.jws.WebService;

/**
 * content
 *
 * @author bl07637
 * @date 9/12/2016
 * @since 0.1.0
 */
@WebService
public interface MyService {
    public int add (int a, int b);

    public int minus (int a, int b);
}
