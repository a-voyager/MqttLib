# lib.Connector --MQTT 封装库
`lib.Connector` 是基于 `MQTT` 协议的客户端/服务端通信库, 主要解决恶劣网络环境通信和数据帧格式的问题, 适用于物联网设备间通信.

[下载 JAR 包][1]

# 特点
 - 简化接口, 使用便捷, 一行代码实现收发数据 
 - 底层封装 `json` 传送, 上层接口仅需传递实体对象即可
 - 恶劣网络下断开连接将自动等待并重连
 - 加入消息队列, 保证恶劣网络消息不丢失

# 用法
## 1. 启动 Apollo
`Apollo` 作为中间件服务器, 启动后记录 `TCP` 协议地址, 如 tcp://192.168.1.100:61613

## 2. 实例化 lib.Connector
两种方式均可实例化 `lib.Connector`:
### a. Builder 模式进行详细配置
```java
    lib.Connector<Message> connector = new lib.Connector.Builder<Message>()
            .setServerURI("tcp://0.0.0.0:61613")    // Apollo 服务器地址
            .setClientId("#1")                      // 本地 ID
            .setClientTopic("client")               // 本地 TopicID
            .setMessageClassType(Message.class)     // 发送消息封装类
            ...
            .build();
```

### b. 使用默认配置
```java
    lib.Connector<Message> connector = lib.Connector.defaultConnector(Message.class, "client"); // 发送消息封装类, 本地 TopicID
```

> 说明
> 1. `Topic ID` 指的是通信话题号, 可以理解为组号, 发送消息时将指定目的组号, 该组号的所有客户端将收到信息
> 2. 消息封装类指的是发送或接收的实体所属类, 该类为自定义类, 在底层将解析为 `json` 进行传输, 此处示例为:
> ```java
>     public class Message {
>         private int id;
>         private String text;
>     
>         public Message(int id, String text) {
>             this.id = id;
>             this.text = text;
>         }
>     }
> ```

## 3. 初始化 lib.Connector
在你的应用生命周期起点, 应该调用初始化函数, 以连接 `Apollo` 服务器和一些其它的初始化操作
```java
    connector.init();
```

## 4. 使用 lib.Connector 发送和接收消息
### a. 发送消息
```java
    connector.sendMessage("server", new Message(2, "Hello World")); // 指定 TopicID 和 消息实体
```

### b. 接收消息
```java
    connector.receiveMessage(new MessageCallBack<Message>() {
        @Override
        public void onNewMessage(Message message) {
            // 新消息来临时回调此函数
            System.out.println(message);
        }
    });
```

# 开源许可
    The MIT License (MIT)

    Copyright (c) 2017 WuHaojie

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
    
  [1]: https://github.com/a-voyager/MqttLib/raw/master/jar/connector.jar