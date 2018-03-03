# -*- encoding: UTF-8 -*-
from tensorflow.examples.tutorials.mnist import input_data

# test
mnist = input_data.read_data_sets("input_data/", one_hot=True)

print("Training data size: ", mnist.train.num_examples)
print("Validating data size: ", mnist.validation.num_examples)
print("Testing data size: ", mnist.test.num_examples)
print("Example training data:", mnist.train.images[0])
print("Example training data label:", mnist.train.labels[0])

# 取出一小部分数据
batch_size=100
xs,ys=mnist.train.next_batch(100)
print("xs shape=",xs.shape)
print("ys shape=",ys.shape)