package mqtest.mqtest.sender;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	private static final int SEND_NUMBER = 5;

	public static void main(String[] args) {

		ConnectionFactory connectionFactory;

		Connection connection = null;

		Session session;

		Destination destination;

		MessageProducer producer;

		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");

		try {

			connection = connectionFactory.createConnection();

			connection.start();

			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

			destination = session.createQueue("FirstQueue");

			producer = session.createProducer(destination);

			// 设置不持久化，仅供学习
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			sendMessage(session, producer);

			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private static void sendMessage(Session session, MessageProducer producer) {
		for (int i = 0; i < SEND_NUMBER; i++) {
			try {

				TextMessage message = session.createTextMessage("你好！！！" + i);

				System.out.println("发送消息你好！！！" + i);
				producer.send(message);
				
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
