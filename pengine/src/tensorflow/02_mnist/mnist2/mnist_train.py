# -*- encoding: UTF-8 -*-
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data

# 1.设置输入和输出节点的个数,配置神经网络的参数。
# 数据集相关
INPUT_NODE = 784  # 输入节点，像素的个数
OUTPUT_NODE = 10  # 输出节点，类别的数目
# 神经网络参数
LAYER1_NODE = 500  # 隐藏层节点数
BATCH_SIZE = 100  # 每次batch打包的样本个数，越小越接近随机梯度下降，越大越接近梯度下降
LEARNING_RATE_BASE = 0.8    # 基础学习率
LEARNING_RATE_DECAY = 0.99  # 学习率的衰减率
REGULARAZTION_RATE = 0.0001 # 损失函数中正则化项系数
TRAINING_STEPS = 5000       # 训练轮数
MOVING_AVERAGE_DECAY = 0.99 # 滑动平均衰减率

# 2. 定义辅助函数来计算前向传播结果，使用ReLU做为激活函数。
def inference(input_tensor, avg_class, weights1, biases1, weights2, biases2):
    # 不使用滑动平均类
    if avg_class == None:
        layer1 = tf.nn.relu(tf.matmul(input_tensor, weights1) + biases1)
        return tf.matmul(layer1, weights2) + biases2
    else:
        # 使用滑动平均类
        layer1 = tf.nn.relu(tf.matmul(input_tensor, avg_class.average(weights1)) + avg_class.average(biases1))
        return tf.matmul(layer1, avg_class.average(weights2)) + avg_class.average(biases2)

def print_acc(sess, round, accuracy, validate_feed, test_feed):
    # 在验证集上的正确率
    validate_acc = sess.run(accuracy, feed_dict=validate_feed)
    # 在测试数据上获取最终的正确率
    test_acc = sess.run(accuracy, feed_dict=test_feed)
    print(("After %d training step(s),"
           "validation accuracy using average model is %g "
           " test accuracy using average model is %g" % (round, validate_acc, test_acc)))

# 训练过程
def train(mnist):
    x = tf.placeholder(tf.float32, [None, INPUT_NODE], name='x-input')
    y_ = tf.placeholder(tf.float32, [None, OUTPUT_NODE], name='y-input')
    # 生成隐藏层的参数。
    # tf.truncated_normal(shape, mean, stddev) :正态分布生成张量，shape是张量的维度，mean是均值，stddev是标准差
    weights1 = tf.Variable(tf.truncated_normal([INPUT_NODE, LAYER1_NODE], stddev=0.1))
    biases1 = tf.Variable(tf.constant(0.1, shape=[LAYER1_NODE]))
    # 生成输出层的参数。
    weights2 = tf.Variable(tf.truncated_normal([LAYER1_NODE, OUTPUT_NODE], stddev=0.1))
    biases2 = tf.Variable(tf.constant(0.1, shape=[OUTPUT_NODE]))
    # 定义训练轮数
    global_step = tf.Variable(0, trainable=False)

    # 【1】向前传播
    # 【1.1】计算不含滑动平均类的前向传播结果
    y = inference(x, None, weights1, biases1, weights2, biases2)
    # 【1.2】计算包含滑动平均类的向前传播结果
    # 定义滑动平均类
    # tf.train.ExponentialMovingAverage，decay衰减率，num_updates控制衰减率
    variable_averages = tf.train.ExponentialMovingAverage(MOVING_AVERAGE_DECAY, global_step)
    # 在所有神经网络参数上使用滑动平均。
    # tf.trainable_variables()返回图上的集合GraphKeys.TRAINABLE_VARIABLES中的元素，这个集合中的元素是所有trainable=True的变量
    variables_averages_op = variable_averages.apply(tf.trainable_variables())
    # 带滑动平均类的向前传播
    average_y = inference(x, variable_averages, weights1, biases1, weights2, biases2)

    # 【2】计算损失函数
    # 【2.1】计算交叉熵及其平均值
    # tf.nn.sparse_softmax_cross_entropy_with_logits，logits是向前传播结果，labels训练数据的真实值
    cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=tf.argmax(y_, 1))
    # 计算当前batch中所有样例的交叉熵的平均值
    cross_entropy_mean = tf.reduce_mean(cross_entropy)
    # 【2.2】L2正则化
    # L2正则化函数
    regularizer = tf.contrib.layers.l2_regularizer(REGULARAZTION_RATE)
    # 计算模型的正则化损失，一般只计算权重
    regularaztion = regularizer(weights1) + regularizer(weights2)
    # 【2.3】计算损失
    # 总损失 = 交叉熵平均值 + 正则化损失
    loss = cross_entropy_mean + regularaztion

    # 【3】反向传播
    # 【3.1】优化损失函数
    # 设置指数衰减的学习率。
    learning_rate = tf.train.exponential_decay(
        LEARNING_RATE_BASE, # 基础学习率
        global_step, # 当前迭代轮数
        mnist.train.num_examples / BATCH_SIZE, #
        LEARNING_RATE_DECAY, # 学习率衰减速度
        staircase=True)
    # 使用tf.train.GradientDescentOptimizer梯度下降优化损失函数
    optimizer = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step=global_step)
    # 【3.2】反向传播更新参数和更新每一个参数的滑动平均值
    # tf支持进行一次完成多个操作，既需要进行optimizer又需要variables_averages_op
    # 例如创建一个group，把train_step和variables_averages_op两个操作放在一起进行，等同于以下操作：
    # with tf.control_dependencies([train_step, variables_averages_op]):
    #     train_op = tf.no_op(name='train')
    train_op = tf.group(optimizer, variables_averages_op)

    # 【4】结果，计算正确率
    # average_y.shape = [None, OUTPUT_NODE]，tf.argmax(average_y, 1)表示返回average_y中最大值的序号
    # Signature: tf.argmax(input, axis=None, name=None, dimension=None, output_type=tf.int64)
    # Returns the index with the largest value across axes of a tensor. (deprecated arguments)
    correct_prediction = tf.equal(tf.argmax(average_y, 1), tf.argmax(y_, 1))
    # 将布尔值转为实数，然后计算平均值，即为正确率。
    accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

    # 【5】初始化会话，并开始训练过程。
    with tf.Session() as sess:
        tf.global_variables_initializer().run()
        # 准备验证数据
        validate_feed = {x: mnist.validation.images, y_: mnist.validation.labels}
        # 准备测试数据
        test_feed = {x: mnist.test.images, y_: mnist.test.labels}

        # 循环的训练神经网络。
        for i in range(TRAINING_STEPS):
            # 每1000轮输出一次在验证集上的正确率
            if i % 1000 == 0:
                print_acc(sess, i, accuracy, validate_feed, test_feed)
            # 产生一轮的batch训练数据
            xs, ys = mnist.train.next_batch(BATCH_SIZE)
            sess.run(train_op, feed_dict={x: xs, y_: ys})
        # 训练结束后，正确率
        print_acc(sess, TRAINING_STEPS, accuracy, validate_feed, test_feed)

        # 保存模型
        saver = tf.train.Saver()
        saver.save(sess, "model_data/model.ckpt")

# 4. 主程序入口，这里设定模型训练次数为5000次。
def main(argv=None):
    mnist = input_data.read_data_sets("../../datasets/MNIST_data", one_hot=True)
    train(mnist)

if __name__=='__main__':
    main()