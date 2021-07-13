
package org.executor.pubsub;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import org.executor.executor.Executor;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {

	public void onSubscribe(String channel, int subscribedChannels) {

		// 订阅成功之后上报客户端的HOSTNAME，已用作任务下发时用
		try {

			InetAddress addr = InetAddress.getLocalHost();

			System.out.println("onsubscribe success" + addr.getHostName());

		} catch (UnknownHostException e) {

			e.printStackTrace();

		}

	}

//	public void onUnsubscribe(String channel, int subscribedChannels) {
//
//	}

	public void onMessage(String channel, String message) {

		System.out.println("onmessage success " + new Date().toString() + ", " + message); // 输出结果日志

		if (message.equals("stop")) {

			Executor executor = new Executor();

			executor.stop();

			return;

		}

		// 调用执行器执行测试用例
		try {

			List<Object> testcases = JSON.parseObject(message).getJSONArray(InetAddress.getLocalHost().getHostName()); // 取分配到本机的用例

			if (testcases != null) {

				Executor executor = new Executor();

				executor.executor(testcases);

			}

		} catch (UnknownHostException e1) {

			e1.printStackTrace();

		} catch (SecurityException e) {

			e.printStackTrace();

		}

	}

//	public void onPUnsubscribe(String pattern, int subscribedChannels) {
//	}
//
//	public void onPSubscribe(String pattern, int subscribedChannels) {
//	}
//
//	public void onPMessage(String pattern, String channel, String message) {
//	}

}
