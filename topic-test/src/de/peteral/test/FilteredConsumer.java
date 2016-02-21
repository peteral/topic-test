package de.peteral.test;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topic/MyTopic"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "Type = 'A'"), })
public class FilteredConsumer implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		Logger.getLogger("test").info("Incoming message: " + msg);

	}

}
