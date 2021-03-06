package com.jengine.apps.apm.zipkin;

import java.util.concurrent.TimeUnit;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.context.log4j2.ThreadContextCurrentTraceContext;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import zipkin2.codec.SpanBytesEncoder;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

public class TraceDemo {

  public static void main(String[] args) {
    Sender sender = OkHttpSender.create("http://localhost:9411/api/v2/spans");
    // 用于上报Span给Zipkin
    AsyncReporter asyncReporter = AsyncReporter.builder(sender)
        .closeTimeout(500, TimeUnit.MILLISECONDS)
        .build(SpanBytesEncoder.JSON_V2);

    // 管理器
    Tracing tracing = Tracing.newBuilder()
        .localServiceName("tracer-demo")
        .spanReporter(asyncReporter)
        .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "user-name"))
        .currentTraceContext(ThreadContextCurrentTraceContext.create())
        .build();

    Tracer tracer = tracing.tracer();
    // 第一条
    // 由Tracer创建一个新的Span，名为encode，然后调用start方法开始计时
    Span span = tracer.newTrace().name("encode").start();
    try {
      // 运行一个比较耗时的方法doSomethingExpensive
      doSomethingExpensive();
    } finally {
      // 调用finish方法结束计时，完成并记录一条跟踪信息。
      span.finish();
    }

    // 第二条，消息跟踪树
    Span twoPhase = tracer.newTrace().name("twoPhase").start();
    try {
      Span prepare = tracer.newChild(twoPhase.context()).name("prepare").start();
      // 业务逻辑
      try {
        prepare();
      } finally {
        // 提交
        prepare.finish();
      }
      Span commit = tracer.newChild(twoPhase.context()).name("commit").start();
      try {
        commit();
      } finally {
        // 提交
        commit.finish();
      }
    } finally {
      twoPhase.finish();
    }


    sleep(1000);

  }

  private static void doSomethingExpensive() {
    sleep(500);
  }

  private static void commit() {
    sleep(500);
  }

  private static void prepare() {
    sleep(500);
  }

  private static void sleep(long milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
