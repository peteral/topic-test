package de.peteral.test.filtered;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.peteral.test.Collector;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topic/Topic"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "Type = 'java:/jms/topic/Topic03'"), })
public class FilteredConsumer03 implements MessageListener {
	@EJB
	private Collector collector;

	@Override
	public void onMessage(Message msg) {
		try {
			collector.track(msg.getStringProperty("Type"), msg.getIntProperty("MessageNo"));
		} catch (JMSException e) {
			Logger.getLogger("test").log(Level.SEVERE, "Failed processing message: ", e);
		}
	}

}
