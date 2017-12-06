package com.jengine.common2.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 目标：
 *
 * package com.jengine.common2.asm;
 * public class C { public void m() throws InterruptedException{ Thread.sleep(100); } }
 *
 * 改为
 *
 * package com.jengine.common2.asm.test;
 * public class C { public static long timer; public void m() throws InterruptedException{ timer -=
 * System.currentTimeMillis(); Thread.sleep(100); timer += System.currentTimeMillis(); System.out.println("Hello World!");} }
 *
 * @author nouuid
 */
public class AddTimeClassAdapter extends ClassAdapter {
  private String owner;
  private boolean isInterface;

  public AddTimeClassAdapter(ClassVisitor cv) {
    super(cv);
  }

  @Override
  public void visit(int version, int access, String name, String signature,
                    String superName, String[] interfaces) {
    name = name.substring(0, name.lastIndexOf("/")) + "/test" + name.substring(name.lastIndexOf("/"));
    cv.visit(version, access, name, signature, superName, interfaces);
    owner = name;
    isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
                                   String signature, String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    if (!name.equals("<init>") && !isInterface && mv != null) {
      //为方法添加计时功能
      mv = new AddTimeMethodAdapter(mv);
    }
    return mv;
  }

  @Override
  public void visitEnd() {
    //添加字段
    if (!isInterface) {
      FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "timer", "J", null, null);
      if (fv != null) {
        fv.visitEnd();
      }
    }
    cv.visitEnd();
  }

  class AddTimeMethodAdapter extends MethodAdapter {
    public AddTimeMethodAdapter(MethodVisitor mv) {
      super(mv);
    }

    @Override
    public void visitCode() {
      mv.visitCode();
      mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
      mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
      mv.visitInsn(Opcodes.LSUB);
      mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
    }

    @Override
    public void visitInsn(int opcode) {
      if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
        mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
        mv.visitInsn(Opcodes.LADD);
        mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello world!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
            "java/io/PrintStream",
            "println",
            "(Ljava/lang/String;)V");
      }
      mv.visitInsn(opcode);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocal) {
      mv.visitMaxs(maxStack + 4, maxLocal);
    }
  }

}
