# -*- encoding: UTF-8 -*-
import os

# tensorboard训练过程可观化

# 训练并生成日志
os.system("python mnist/mnist_with_summaries.py --data_dir=input_data --log_dir=logs/mnist_with_summaries")

# 对日志进行可视化
# os.system("tensorboard --logdir=logs/mnist_with_summaries")
os.system("tensorboard --logdir=/tmp/tensorflow/mnist/logs/mnist_with_summaries")