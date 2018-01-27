package  com.jengine.common.javacommon.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * content
 *
 * @author nouuid
 * @date 10/27/2016
 * @since 0.1.0
 */
public class ClassLocationUtils {

  private final static String FILE_STR = "file";

  /**
   * 获取类所有的路径 可在执行到断点处，按alt+F8动态执行代码（intelij idea），
   * 如输入： ClassLocationUtils.findPath(org.objectweb.asm.ClassVisitor.class)
   */
  public static String findPath(final Class cls) {
    if (cls == null) {
      throw new IllegalArgumentException("null input: cls");
    }
    URL result = null;
    String clsAsResource = cls.getName().replace('.', '/').concat(".class");
    final ProtectionDomain protectionDomain = cls.getProtectionDomain();
    if (protectionDomain != null) {
      final CodeSource codeSource = protectionDomain.getCodeSource();
      if (codeSource != null) {
        result = codeSource.getLocation();
      }
      if (result != null && FILE_STR.equals(result.getProtocol())) {
        try {
          if (result.toExternalForm().endsWith(".jar") ||
              result.toExternalForm().endsWith(".zip")) {
            result = new URL("jar:".concat(result.toExternalForm())
                .concat("!/").concat(clsAsResource));
          } else if (new File(result.getFile()).isDirectory()) {
            result = new URL(result, clsAsResource);
          }
        } catch (MalformedURLException ignore) {

        }
      }
    }
    if (result == null) {
      final ClassLoader clsLoader = cls.getClassLoader();
      result = clsLoader != null ?
          clsLoader.getResource(clsAsResource) :
          ClassLoader.getSystemResource(clsAsResource);
    }
    return result.toString();
  }
}
