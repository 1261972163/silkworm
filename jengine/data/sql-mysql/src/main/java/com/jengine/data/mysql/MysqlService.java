package com.jengine.data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang.StringUtils;

/**
 * @author nouuid
 * @date 6/30/2016
 * @description
 */
public class MysqlService {

  private String url;
  private Connection conn = null;
  private PreparedStatement stmt = null;

  public void init(String url, String username, String password) throws Exception {
    if (StringUtils.isBlank(url)) {
      throw new Exception("blank url");
    }

    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(url, username, password);
      System.out.println("connected to mysql.");

    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (stmt != null && !stmt.isClosed()) {
          stmt.close();
        }
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
    }
  }

  public boolean doUpdate(String sql) {
    int result = 0;
    try {
      stmt = conn.prepareStatement(sql);
      result = stmt.executeUpdate();
      if (result != -1) {
        return true;
      } else {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public ResultSet doQuery(String sql) {
    ResultSet rs = null;
    try {
      stmt = conn.prepareStatement(sql);
      rs = stmt.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }

  public void close() {
    try {
      if (stmt != null && !stmt.isClosed()) {
        stmt.close();
      }
      if (conn != null && !conn.isClosed()) {
        conn.close();
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
  }
}
