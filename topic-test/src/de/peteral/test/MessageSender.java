package de.peteral.test;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
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

	@POST
	public void sendMessage(@FormParam("type") String messageType, @FormParam("payload") String payload)
			throws JMSException {

		TextMessage message = context.createTextMessage(payload);
		message.setStringProperty("Type", messageType);
		context.createProducer().send(topic, message);

		Logger.getLogger("test").info("Sending message: " + message);
	}
}
