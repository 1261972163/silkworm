package com.jengine.common2.loggerDynamicLevel;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author nouuid
 */
public class DemoServerServlet extends HttpServlet {

  private static LoggerController loggerController = new LoggerController("jetty");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    System.out.println("### " + req);
    loggerController.index(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    doGet(req, resp);
  }
}
