/**
 * 延迟消息结构体
 */
package com.delaymessage.mq;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

/**
 * MQ延迟消息体
 * 
 * @author glz
 *
 */
public class DelayMessage implements Delayed {

	/**
	 * MQ消息体
	 */
	private JSONObject jsonMessage;

	public JSONObject getJsonMessage() {
		return jsonMessage;
	}

	/**
	 * 消息延期发送时间
	 */
	private Date delayDate;

	public Date getDelayDate() {
		return delayDate;
	}

	/**
	 * 消息延期发送时间毫秒
	 */
	private long delayLong;

	public DelayMessage(JSONObject jsonMessage, Date delayDate) {
		this.jsonMessage = jsonMessage;
		this.delayDate = delayDate;
		this.delayLong = delayDate.getTime();
	}

	/**
	 * 按发现消息的延迟时间先后顺序，把消息压入延迟队列
	 */
	@Override
	public int compareTo(Delayed o) {
		final DelayMessage other = (DelayMessage) o;
		if (this.delayLong < other.delayLong) {
			return -1;
		} else if (this.delayLong > other.delayLong) {
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return this.delayLong - new Date().getTime();
	}

	/**
	 * 重载toString方法，以消息体的json字符作为消息体是否存在的依据
	 */
	@Override
	public String toString() {
		return jsonMessage.toJSONString();
	}
}
