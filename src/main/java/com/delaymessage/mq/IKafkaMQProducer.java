/*
 * Kafka生产者封装类，根据业务场景封装通用接口方法
 */

package com.delaymessage.mq;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.delaymessage.exception.DelayMessageException;

public interface IKafkaMQProducer {

	/**
	 * 发送消息
	 * @param jsonObject
	 * @throws DelayMessageException
	 */
	public void sendMessage(JSONObject jsonObject) throws DelayMessageException;
	
	/**
	 * 初始化生产者
	 * @param kafkaServer
	 * @param topic
	 */
	public void initProducer(String kafkaServer,String topic);
	
	/**
	 * 关闭kafak连接
	 */
	public void close();
	
	/**
	 * 发送延迟消息
	 * @param jsonObject
	 * @param delay
	 * @throws DelayMessageException
	 */
	public void sendMessage(JSONObject jsonObject,Date delay) throws DelayMessageException;
	
	/**
	 * 装载延迟任务
	 * @param delayMessage
	 */
	public void pushTask(DelayMessage delayMessage);
	
	/**
	 * 异常延迟任务
	 * @param delayMessage
	 */
	public void removeTask(DelayMessage delayMessage);
	
	/**
	 * 获取延迟队列长度
	 */
	public int getDelayQueueSize();
}
