# -*- encoding: UTF-8 -*-
import tensorflow as tf

# 1. 定义计算图，不定义时会使用默认计算图tf.get_default_graph()
# 2. 在计算图中定义张量，指向计算结果
a = tf.constant([1.0, 2.0], name='a')
b = tf.constant([3.0, 4.0], name='b')
ab = a + b
# 3. 在会话中运行计算图，默认运行默认计算图
with tf.Session() as sess:
    result = sess.run(ab)
# 4. 获取结果
print result
