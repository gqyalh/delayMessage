/*
 * kafka消费者接口
 */
package com.delaymessage.mq;

/**
 * 封装kafak消费的基础代码
 * @author glz
 *
 */
public interface IKafkaMQConsumer {

	/**
	 * 消费消息
	 */
	public void consumeMessage();
	
	/**
	 * 初始化消费者
	 * @param kafkaServer
	 * @param topic
	 * @param groupId
	 */
	public void initConsumer(String kafkaServer,String topic,String groupId);
	
	/**
	 * 启动消费者消费监听进程
	 */
	public void start();
}
