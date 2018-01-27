package com.jengine.common.loggerDynamicLevel;

import com.jengine.common.javacommon.utils.StringUtils;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 动态修改log4j日志级别
 *
 * @author nouuid
 * @since 0.1.0
 */
public class LoggerController {

  String webRoot = "jetty";

  public LoggerController(String webRoot) {
    this.webRoot = "/" + webRoot + "/logger";
  }
  /**
   * @param p 需要修改的包名
   * @param l 日志级别
   */
  private String change(String p, String l) {
    try {
      Level level = Level.toLevel(l);
      Logger logger = LogManager.getLogger(p);
      logger.setLevel(level);
    } catch (Exception e) {
      return "failed";
    }
    return "success";
  }


  /**
   * 修改全局日志级别，但是我测试的时候只修改了第三方jar的日志级别，我的项目包的日志级别没有修改成功 可获取到Logger们后循环遍历设置
   */
  private String change(String l) {
    try {
      Level level = Level.toLevel(l);
      LogManager.getRootLogger().setLevel(level);
    } catch (Exception e) {
      return "failed";
    }
    return "success";
  }

  /**
   * 查看现在包的日志级别
   */
  private void show(HttpServletRequest request, HttpServletResponse response) {
    StringBuilder sb = new StringBuilder();
    try {
      sb.append("<html>");
      sb.append("<table border=\"1\">\n" +
          "  <tr>\n" +
          "    <th>Package</th>\n" +
          "    <th>Level</th>\n" +
          "  </tr>");
      Enumeration logs = LogManager.getCurrentLoggers();
      while (logs.hasMoreElements()) {
        Logger logger = (Logger) logs.nextElement();
        sb.append("<tr>");
        sb.append("<td>" + logger.getName() + "</td>");
        sb.append("<td>" + logger.getEffectiveLevel() + "</td>");
        sb.append("</tr>");
      }
      sb.append("</table>");
      sb.append("</html>");
      response.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=utf-8");
      Writer writer = response.getWriter();
      writer.write(sb.toString());
      writer.flush();
      if (writer != null) {
        writer.close();
      }
      System.out.println(sb.toString());
    } catch (Exception e) {
    }
  }

  /**
   * 日志调整的统一访问接口
   */
  public void index(HttpServletRequest request, HttpServletResponse response) {
    String url = request.getRequestURI().toString();
    System.out.println(url);
    if (url.startsWith(webRoot + "/change")) {
      String apackage = request.getParameter("package");
      String level = request.getParameter("level");
      if (StringUtils.isNotBlank(apackage) && StringUtils.isNotBlank(level)) {
        change(apackage, level);
      } else if (!StringUtils.isBlank(level)) {
        change(level);
      }
    } else if (url.startsWith(webRoot + "/show")) {
//      show(request, response);
    }
    show(request, response);
  }

}