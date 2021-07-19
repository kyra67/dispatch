
package org.executor.pubsub;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Logger;

import org.executor.executor.Executor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.JedisPubSub;

@Component
public class Subscriber extends JedisPubSub {

	public void onSubscribe(String channel, int subscribedChannels) {

		Logger logger = Logger.getGlobal();

		logger.info("onsubscribe success");

	}

//	public void onUnsubscribe(String channel, int subscribedChannels) {
//
//	}

	public void onMessage(String channel, String message) {

		Logger logger = Logger.getGlobal();

		logger.info("onmessage success " + new Date().toString()); // 输出结果日志

		if (message.equals("stop")) {

			Executor executor = new Executor();

			executor.stop();

			return;

		}

		// 调用执行器执行测试用例
		try {

			List<Object> testcases = JSON.parseObject(message).getJSONArray(InetAddress.getLocalHost().getHostName()); // 取分配到本机的用例

			logger.info(testcases.toString());

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
