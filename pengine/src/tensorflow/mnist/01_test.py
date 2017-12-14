# -*- encoding: UTF-8 -*-
from tensorflow.examples.tutorials.mnist import input_data

# test
mnist = input_data.read_data_sets("input_data/", one_hot=True)

print "ok"

