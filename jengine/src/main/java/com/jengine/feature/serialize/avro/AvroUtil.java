package com.jengine.feature.serialize.avro;

import org.apache.avro.Protocol;

import java.io.File;
import java.io.IOException;

/**
 * @author bl07637
 * @date 4/14/2016
 * @description
 */
public class AvroUtil {
    public static Protocol getProtocol(String path) {
        Protocol protocol = null;
        try {
            protocol = Protocol.parse(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocol;
    }
}
