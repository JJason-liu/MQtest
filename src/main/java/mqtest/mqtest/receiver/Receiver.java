package mqtest.mqtest.receiver;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {

	public static void main(String[] args) {
		// 连接工厂
		ConnectionFactory connectionFactory;
		// 连接
		Connection connection = null;

		Session session;
		// 消息的目的地，发送给谁
		Destination destination;
		// 消费者，消息的接受者
		MessageConsumer consumer;

		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");

		try {
			//构建从工厂得到的连接对象
			connection = connectionFactory.createConnection();
			
			//启动
			connection.start();
			
			//获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			
			destination = session.createQueue("SendQueue");
			
			consumer = session.createConsumer(destination);
			
			while (true) {
				//设置消息接收消息的时间，为了便于测试，这里暂定为100s
				TextMessage message = (TextMessage) consumer.receive(100000);
				if(message != null){
					System.out.println("接收到消息："+message.getText());
				}else{
					break;
				}
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
