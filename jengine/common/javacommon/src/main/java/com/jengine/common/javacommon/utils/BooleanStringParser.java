/**
 * Copyright (C) 2017 The BEST Authors
 */

package  com.jengine.common.javacommon.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * @author bl07637
 */
public class BooleanStringParser {

  // thread safe
  public static ScriptEngineManager manager = new ScriptEngineManager();

  private static Map<String, CompiledScriptSafe> stringScriptEngineMap = new ConcurrentHashMap<String,
      CompiledScriptSafe>();

  public static void main(String[] args) throws ScriptException, InterruptedException {
    String rule1 = "(rule1 or rule2) and r3";
    BooleanStringParser booleanStringParser = new BooleanStringParser();
    booleanStringParser.addScritp("rule1", rule1);
    Map<String, Boolean> values = new HashMap<String, Boolean>();
    values.put("rule1", true);
    values.put("rule2", false);
    values.put("r3", false);
    boolean res = booleanStringParser.exec("rule1", values);
    System.out.println(res);

//    Random random = new Random();
//
//    for (int i = 0; i < 20; i++) {
//      Thread thread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//          int j = 1;
//          while (j < 10000) {
//            Map<String, Boolean> values = new HashMap<String, Boolean>();
//            boolean value1 = j%2==0 ? true : false;
//            boolean value2 = j%3==0 ? false : true;
//            boolean value3 = j%7==0 ? true : false;
//            values.put("a", value1);
//            values.put("b", value2);
//            values.put("r3", value3);
//            boolean res = false;
//            try {
//              res = booleanStringParser.exec("rule1", values);
//              Thread.sleep(random.nextInt(500));
//            } catch (ScriptException e) {
//              e.printStackTrace();
//            } catch (InterruptedException e) {
//              e.printStackTrace();
//            }
//            boolean actual = (value1 || value2) && value3;
//            if (actual != res) {
//              System.out.println(Thread.currentThread().getName() + "-" + j + "  ##########----->" +
//                  " " +
//                  actual + " " + "/ " + res);
//
//            } else {
//              System.out.println(Thread.currentThread().getName() + "-" + j + "-----> " +
//                  actual + " " + "/ " + res);
//            }
//            j++;
//          }
//
//        }
//      }, "t" + i);
//      thread.start();
//    }
//    Thread.sleep(30*60*1000);
  }

  /**
   * 添加脚本
   */
  public void addScritp(String scriptName, String script) throws ScriptException {
    final CompiledScriptSafe compiledScriptSafe = new CompiledScriptSafe(manager, script);
    stringScriptEngineMap.put(scriptName, compiledScriptSafe);
  }

  /**
   * 执行脚本
   */
  public boolean exec(String scriptName, Map<String, Boolean> values) throws
      ScriptException {
    CompiledScriptSafe compiledScriptSafe = stringScriptEngineMap.get(scriptName);
    return compiledScriptSafe.eval(values);
  }

  /**
   * 线程安全的CompiledScript。
   * 不使用GLOBAL_SCOPE域时，CompiledScript是线程安全的。
   * 使用GLOBAL_SCOPE域时，CompiledScript是非线程安全的，需要使用evalSafe
   */
  class CompiledScriptSafe {
    private final CompiledScript compiledScript;
    private Lock lock = new ReentrantLock();

    CompiledScriptSafe(ScriptEngineManager manager, String script) throws ScriptException {
      String scriptEngineStr = script;
      scriptEngineStr = scriptEngineStr.replaceAll("or", "||");
      scriptEngineStr = scriptEngineStr.replaceAll("and", "&&");
      ScriptEngine scriptEngine = manager.getEngineByName("js");
      compiledScript = ((Compilable) scriptEngine).compile(scriptEngineStr);
    }

    public boolean eval(Map<String, Boolean> values) throws ScriptException {
      Bindings bindings = new SimpleBindings();
      for (String key : values.keySet()) {
        bindings.put(key, values.get(key));
      }
      Object result = compiledScript.eval(bindings);
      return (boolean) result;
    }

    public boolean evalGlobalSafe(Map<String, Boolean> values) throws ScriptException {
      try {
        lock.lock();
        return eval(values);
      } finally {
        lock.unlock();
      }
    }

  }
}
