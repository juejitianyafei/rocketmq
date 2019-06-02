---
title: Springboot集成ROcketMQ
categories: [Springboot],[MQ]
date: 2018-05-23 10:43:12
tags:
---

spring boot 集成rocketmq示例

###window下载安装RocketMQ

```
# 环境准备   JDK1.8、Maven、Git
# 下载 http://rocketmq.apache.org/release_notes/release-notes-4.4.0/ 
# 系统环境变量配置
         变量名：ROCKETMQ_HOME
         变量值：MQ解压路径\MQ文件夹名
```

###启动NameServer，启动后NameServer的端口是9876，请确保自己的9876端口未被占用
```
#  Cmd命令框执行进入至‘MQ文件夹\bin’下，然后执行‘start mqnamesrv.cmd’，启动NAMESERVER。成功后会弹出提示框，此框勿关闭。

```

### 启动Broker
```
# Cmd命令框执行进入至‘MQ文件夹\bin’下，然后执行‘start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true’，启动BROKER。成功后会弹出提示框，此框勿关闭
# 假如弹出提示框提示‘错误: 找不到或无法加载主类 xxxxxx’。打开runbroker.cmd，然后将‘%CLASSPATH%’加上英文双引号。保存并重新执行start语句
```
### RocketMQ插件部署
```
# 地址：https://github.com/apache/rocketmq-externals.git  或者直接下载我上传的文件
  
  下载完成之后，进入‘rocketmq-externals\rocketmq-console\src\main\resources’文件夹，打开‘application.properties’进行配置。
   rocketmq.config.namesrvAddr=127.0.0.1:9876
# 进入‘\rocketmq-externals\rocketmq-console’文件夹，执行‘mvn clean package -Dmaven.test.skip=true’，编译生成
# 编译成功之后，Cmd进入‘target’文件夹，执行‘java -jar rocketmq-console-ng-1.0.0.jar’，启动‘rocketmq-console-ng-1.0.0.jar’
#  浏览器中输入‘127.0.0.1：配置端口’，成功后即可查看
```
