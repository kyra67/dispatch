package org.executor.start;

import javax.annotation.PostConstruct;

import org.executor.pubsub.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.*;

@Component
public class StartSub {

	private String channel = "mytest";

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

	}

}
