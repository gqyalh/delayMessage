/**
 * 生产者抽象实现，实现基本的生产者逻辑
 */
package com.delaymessage.mq;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

public abstract class AbstractProducer implements IKafkaMQProducer {

	protected String topic;
	protected Producer<String, String> producer;
	protected String kafkaServer;

	/**
	 * 生产者初始化，
	 * 1.完成topic绑定；2.完成kafka服务服务绑定；3.完成kafka生产者客户端实例化
	 */
	@Override
	public void initProducer(String kafkaServer, String topic) {
		this.topic = topic;
		this.kafkaServer = kafkaServer;
		Properties properties = new Properties();
		properties.put("bootstrap.servers", kafkaServer);
		properties.put("retries", 0);
		properties.put("key.serializer", StringSerializer.class.getName());
		properties.put("value.serializer", StringSerializer.class.getName());
		producer = new KafkaProducer<String, String>(properties);
	}

	/**
	 * 关闭生产者和kafka服务的连接
	 */
	@Override
	public void close() {
		producer.close();
	}

}
