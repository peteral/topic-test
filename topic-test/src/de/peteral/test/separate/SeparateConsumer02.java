package de.peteral.test.separate;

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
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/topic/Topic02"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"), })
public class SeparateConsumer02 implements MessageListener {
	@EJB
	private Collector collector;

	@Override
	public void onMessage(Message msg) {
		try {
			collector.track("Topic02", msg.getIntProperty("MessageNo"));
		} catch (JMSException e) {
			Logger.getLogger("test").log(Level.SEVERE, "Failed processing message: ", e);
		}
	}

}
