package org.distribute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
public class StopCaseController {

	private String channel = "mytest";

	@Autowired
	JedisPool jedisPool;

	@RequestMapping(value = "stop", method = RequestMethod.GET)
	public void stopCase() {

		startPub("stop");

	}

	/*
	 * Redis发布
	 */
	public void startPub(String msg) {

		Jedis publishJedis = jedisPool.getResource();

		publishJedis.publish(channel, msg);

	}

}
