package com.jengine.apps.apm.opencensus;

import java.io.IOException;

import io.opencensus.contrib.zpages.ZPageHandlers;

public class TraceDemo {

  public static void main(String[] args) throws IOException, InterruptedException {
    ZPageHandlers.startHttpServerAndRegisterAll(3000);
    System.out.println("started.");

    Thread.sleep(30*60*1000);

  }
}
