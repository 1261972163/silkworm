package com.jengine.data.opencensus;

import io.opencensus.contrib.zpages.ZPageHandlers;

import java.io.IOException;

public class TraceDemo {

  public static void main(String[] args) throws IOException, InterruptedException {
    ZPageHandlers.startHttpServerAndRegisterAll(3000);
    System.out.println("started.");

    Thread.sleep(30*60*1000);

  }
}
