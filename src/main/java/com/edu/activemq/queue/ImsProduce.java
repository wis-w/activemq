package com.edu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ImsProduce {

    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        // 1 创建链接工厂,按照指定的url指定，采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2 通过连接工厂，获取connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 3 创建会话session
        // 参数1：事务 参数2：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4 创建目的地(具体是队列或者主题topic)
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5 创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        // 6 通过使用消息的生产者发送三条消息到MQ队列
        for (int i = 0; i < 3; i++) {
            // 7 创建消息
            TextMessage textMessage = session.createTextMessage("msg:" + i);
            // 8 发送消息
            producer.send(textMessage);
        }
        // 9 关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("消息通讯结束");
    }
}
