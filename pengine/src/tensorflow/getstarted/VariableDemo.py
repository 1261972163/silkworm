# -*- encoding: UTF-8 -*-
import tensorflow as tf

# constant不可变，不需要初始化
a = tf.constant(1)
# Variable是可变变量，初始化后才能使用
b = tf.Variable(3, dtype=tf.int32)
c = a+b
sess = tf.Session()
# Variable需要显示进行初始化
init = tf.global_variables_initializer()
sess.run(init)
d = sess.run(c)
print d

