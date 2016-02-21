package de.peteral.test;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("send")
public class MessageSender {
	@Inject
	private JMSContext context;

	@Resource(lookup = "java:/jms/topic/MyTopic")
	private Topic topic;

	private String[] TOPICS = { "java:/jms/topic/Topic01", "java:/jms/topic/Topic02", "java:/jms/topic/Topic03",
			"java:/jms/topic/Topic04", "java:/jms/topic/Topic05", "java:/jms/topic/Topic06", "java:/jms/topic/Topic07",
			"java:/jms/topic/Topic08", "java:/jms/topic/Topic09", "java:/jms/topic/Topic10" };

	@EJB
	private Collector collector;

	@Path("single")
	@POST
	public void sendMessage(@FormParam("type") String messageType, @FormParam("payload") String payload)
			throws JMSException {

		TextMessage message = context.createTextMessage(payload);
		message.setStringProperty("Type", messageType);
		context.createProducer().send(topic, message);

		Logger.getLogger("test").info("Sending message: " + message);
	}

	@Path("filtered")
	@POST
	public void filteredTest(@FormParam("count") int count) throws JMSException {
		collector.startTest(count);

		JMSProducer producer = context.createProducer();
		for (int i = 0; i < count; i++) {
			for (String topicName : TOPICS) {
				producer.send(topic, createTestMessage(topicName, i));
			}
		}
	}

	private Message createTestMessage(String topic, int messageNo) throws JMSException {
		Message message = context.createMessage();
		message.setStringProperty("Type", topic);
		message.setIntProperty("MessageNo", messageNo);

		return message;
	}

	@Path("separate")
	@POST
	public void separateTest(@FormParam("count") int count) throws JMSException {

	}
}
