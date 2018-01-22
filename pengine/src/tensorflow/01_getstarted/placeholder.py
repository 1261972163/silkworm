# -*- encoding: UTF-8 -*-
import tensorflow as tf

# placeholder占位符
a = tf.placeholder(tf.float32)
b = tf.placeholder(tf.float32)
c = a + b
sess = tf.Session()
d = sess.run(c, {a:3, b:4})
e = sess.run(c, {a:[[3]], b:[[4]]})
f = sess.run(c, {a:[[3,4],[1,2]], b:[[4,3],[5,6]]})
print d
print e
print f



