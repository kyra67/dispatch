package org.distribute.redisconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
//@ConfigurationProperties("spring.redis")
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.port}")
	private int port;

	@Bean
	public JedisPool jedisPoolFactory() {

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 0, password);

		return jedisPool;

	}

//	public String getHost() {
//		return host;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public int getPort() {
//		return port;
//	}
//
//	public void setHost(String host) {
//		this.host = host;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}
//
//	public String getChannel() {
//		return channel;
//	}
//
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}

}
