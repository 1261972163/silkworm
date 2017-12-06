# -*- encoding: UTF-8 -*-
import tensorflow as tf

# TensorFlow 程序通常被组织成一个构建阶段和一个执行阶段。

#--------------------------------------------------------
# 1. 构建阶段
#--------------------------------------------------------
# 1.1 TensorFlow Python 库有一个默认图 (default graph), op 构造器可以为其增加节点
# 1.2 创建源op，源op不需要任何输入op
matrix1 = tf.constant([[3.,3.]])
# 1.3 创建另外一个常量 op
matrix2 = tf.constant([[2.],[2.]])
# 1.4 创建一个矩阵乘法 matmul op , 把 'matrix1' 和 'matrix2' 作为输入
product = tf.matmul(matrix1, matrix2)
# 1.5 默认图现在有三个节点, 两个 constant() op, 和一个matmul() op. 为了真正进行矩阵相乘运算, 并得到矩阵乘法的 结果, 你必须在会话里启动这个图.

#--------------------------------------------------------
# 2. 执行阶段
#--------------------------------------------------------
# 2.1 创建一个 Session 对象, 如果无任何创建参数, 会话构造器将启动默认图.
sess = tf.Session()
# 2.2 调用 sess 的 'run()' 方法来执行矩阵乘法 op, 传入 'product' 作为该方法的参数.
result = sess.run(product)
# 2.3 返回值 'result' 是一个 numpy `ndarray` 对象.
print result
# 2.4 任务完成, 关闭会话.
sess.close()