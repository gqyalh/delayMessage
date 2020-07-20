/*
 * 延迟队列消息发送producer
 */
package com.delaymessage.mq;

import java.util.concurrent.DelayQueue;

import com.delaymessage.exception.DelayMessageException;

/**
 * 延迟消息生产者队列
 * @author glz
 *
 */
public abstract class AbstractDelayProducer extends AbstractProducer {
	protected static DelayQueue<DelayMessage> delayQueue = new DelayQueue<>();

	@Override
	public void initProducer(String kafkaServer, String topic) {
		super.initProducer(kafkaServer, topic);
		startDelayTask();
	}

	/**
	 *  开启延迟队列监听进程
	 */
	private void startDelayTask() {
		new Thread() {
			public void run() {
				while (true) {
					DelayMessage delayMessage = null;
					try {
						while ((delayMessage = delayQueue.take()) != null) {
							sendMessage(delayMessage.getJsonMessage());
						}
					} catch (InterruptedException | DelayMessageException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	/**
	 * 当重启服务后，可以重新装载持久化后待发送的延迟消息
	 */
	@Override
	public void pushTask(DelayMessage delayMessage) {
		delayQueue.add(delayMessage);
	}

	/**
	 * 取消延迟任务，从延迟队列中移除消息体
	 */
	@Override
	public void removeTask(DelayMessage delayMessage) {
		delayQueue.remove(delayMessage);
	}

	/**
	 * 获取延迟消息队列深度
	 */
	@Override
	public int getDelayQueueSize() {
		return delayQueue.size();
	}

}
