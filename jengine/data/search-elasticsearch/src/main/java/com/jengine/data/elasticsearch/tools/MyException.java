package com.jengine.data.elasticsearch.tools;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author nouuid
 * @date 8/11/2016
 * @description
 */
public class MyException extends NestableRuntimeException {

  private static final long serialVersionUID = -6287830234152662109L;

  private String errorCode;
  private String errorDesc;

  public MyException(String errorCode) {
    super(errorCode);
  }

  public MyException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public MyException(String errorCode, String errorDesc) {
    super(errorCode + ":" + errorDesc);
  }

  public MyException(String errorCode, String errorDesc, Throwable cause) {
    super(errorCode + ":" + errorDesc, cause);
  }

  public MyException(Throwable cause) {
    super(cause);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorDesc() {
    return errorDesc;
  }

  @Override
  public Throwable fillInStackTrace() {
    return this;
  }
}
