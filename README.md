# Traffic Risk Prediction

#### 介绍
交通风险预测

#### 运行环境
+ intel i3-8100
+ 8G 内存
+ IntelliJ IDEA 2020.1.3

#### 数据来源
https://gaia.didichuxing.com
#### 数据处理过程
+ 将订单id和司机id相同的GPS数据整合到同一个对象中
+ 去噪声，删除异常定位点和异常订单
+ 读取对象的GPS数据，计算距离、时间、速度等相关特征
+ 按日期将若干特征写入csv文档