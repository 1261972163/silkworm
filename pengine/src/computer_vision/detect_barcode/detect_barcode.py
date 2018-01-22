# -*- encoding: UTF-8 -*-

import numpy as np
# 解析命令行参数
import argparse
import cv2

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()

# –image是指包含条形码的待检测图像文件的路径。
ap.add_argument("-i", "--image", required = True, help = "path to the image file")
args = vars(ap.parse_args())

# 从磁盘载入图像并转换为灰度图。
image = cv2.imread(args["image"])
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# 使用Scharr操作（指定使用ksize = -1）构造灰度图在水平和竖直方向上的梯度幅值表示。
gradX = cv2.Sobel(gray, ddepth = cv2.cv.CV_32F, dx = 1, dy = 0, ksize = -1)
gradY = cv2.Sobel(gray, ddepth = cv2.cv.CV_32F, dx = 0, dy = 1, ksize = -1)

# Scharr操作之后，我们从x-gradient中减去y-gradient，通过这一步减法操作，最终得到包含高水平梯度和低竖直梯度的图像区域。
gradient = cv2.subtract(gradX, gradY)
gradient = cv2.convertScaleAbs(gradient)

# 通过去噪仅关注条形码区域。
# 使用9*9的内核对梯度图进行平均模糊，这将有助于平滑梯度表征的图形中的高频噪声。
blurred = cv2.blur(gradient, (9, 9))
# 将模糊化后的图形进行二值化，梯度图中任何小于等于255的像素设为0（黑色），其余设为255（白色）。
(_, thresh) = cv2.threshold(blurred, 225, 255, cv2.THRESH_BINARY)

# 在上面的二值化图像中，条形码的竖杠之间存在缝隙，
# 为了消除这些缝隙，并使我们的算法更容易检测到条形码中的“斑点”状区域，
# 我们需要进行一些基本的形态学操作

# 构造一个长方形内核。这个内核的宽度大于长度，因此我们可以消除条形码中垂直条之间的缝隙。
kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (21, 7))
# 将上一步得到的内核应用到我们的二值图中，以此来消除竖杠间的缝隙。
closed = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, kernel)

# 消除这些小斑点
# 首先进行4次腐蚀（erosion），然后进行4次膨胀（dilation）。
# 腐蚀操作将会腐蚀图像中白色像素，以此来消除小斑点，而膨胀操作将使剩余的白色像素扩张并重新增长回去。
closed = cv2.erode(closed, None, iterations = 4)
closed = cv2.dilate(closed, None, iterations = 4)

# 找到图像中条形码的轮廓
# 简单地找到图像中的最大轮廓，如果我们正确完成了图像处理步骤，这里应该对应于条形码区域
(cnts, _) = cv2.findContours(closed.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
c = sorted(cnts, key = cv2.contourArea, reverse = True)[0]

# 为最大轮廓确定最小边框
rect = cv2.minAreaRect(c)
box = np.int0(cv2.cv.BoxPoints(rect))

# 显示检测到的条形码
cv2.drawContours(image, [box], -1, (0, 255, 0), 3)
cv2.imshow("Image", image)
cv2.waitKey(0)