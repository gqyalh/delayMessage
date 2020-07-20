package com.delaymessage.mq;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public abstract class AbstractConsumer implements IKafkaMQConsumer {
	protected KafkaConsumer<String, String> consumer;

	public abstract void consumeMessage();

	private IKafkaMQConsumer target;

	@Override
	public void initConsumer(String kafkaServer, String topic, String groupId) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", kafkaServer);
		properties.put("group.id", groupId);
		properties.put("value.deserializer", StringSerializer.class.getName());
		properties.put("key.serializer", StringSerializer.class.getName());
		properties.put("key.deserializer", StringDeserializer.class.getName());
		properties.put("value.deserializer", StringDeserializer.class.getName());
		try {
			consumer = new KafkaConsumer<String, String>(properties);
			consumer.subscribe(Arrays.asList(topic));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public synchronized void start() {
		if (target == null) {
			target = this;
		}
		new Thread() {
			public void run() {
				while (true) {
					try {
						target.consumeMessage();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
