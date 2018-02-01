/**
 * 
 */
package in.jms.apache.activemq.producer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author Prasad Boini
 *
 */
public class MessageSender {

	// private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	public static void main(String[] args) {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("TEST.FOO");

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			for (int i = 0; i < 10; i++) {

				// Create a messages
				String text = "Hello world! From: " + Thread.currentThread().getName() + " : ";
				TextMessage message = session.createTextMessage(text);

				// Tell the producer to send the message
				System.out.println(
						"Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName() + ": " + i);
				producer.send(message);
				Thread.sleep(5000);
			}
			// Clean up
			session.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}
