package de.peteral.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class Collector {

	private int count;

	private Map<String, Long> firstMessage = new ConcurrentHashMap<>();

	public void startTest(int count) {
		this.count = count;
		firstMessage.clear();
	}

	public void track(String topic, int messageNo) {
		if (messageNo == 0) {
			firstMessage.put(topic, System.currentTimeMillis());
			return;
		}

		if (messageNo == (count - 1)) {
			long duration = System.currentTimeMillis() - firstMessage.get(topic);

			Logger.getLogger("test")
					.info("Topic [" + topic + "] duration: " + duration + " [ms] for " + (messageNo + 1) + " messages");
		}
	}

}
