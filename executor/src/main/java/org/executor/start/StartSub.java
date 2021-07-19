package org.executor.start;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.executor.pubsub.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.*;

@Component
public class StartSub {

	private String channel = "mytest";

	Logger logger = Logger.getGlobal();

	@Autowired
	JedisPool jedisPool;

	@PostConstruct
	public void startSub() {

		Subscriber subscriber = new Subscriber();

		Jedis subscriberJedis = jedisPool.getResource();

		new Thread() {

			public void run() {

				subscriberJedis.subscribe(subscriber, channel);

			}

		}.start();

		HostnameStore();

	}

	// 启动时上报客户端的hostname存储在Redis服务器中
	public void HostnameStore() {

		try {

			InetAddress addr = InetAddress.getLocalHost();

			logger.info(addr.getHostName());

			Jedis jedis = jedisPool.getResource();

			jedis.sadd("hostnames", addr.getHostName());

		} catch (UnknownHostException e) {

			e.printStackTrace();

		}

	}

}
