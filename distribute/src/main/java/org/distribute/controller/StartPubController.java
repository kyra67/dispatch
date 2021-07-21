package org.distribute.controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.*;

@Controller
public class StartPubController {

	Logger logger = Logger.getGlobal();

	Random random;

	String channel = "mytest";

	// 测试用例json格式
	String test_cases = "{\"cases\":[{\"classname\":\"org.executor.testcase.HttpGet\",\"url\":\"https://ssl.gongyi.qq.com/cgi-bin/gywcom_qry_donate_dynamic\"},"
			+ "{\"classname\":\"org.executor.testcase.HttpPost\",\"url\":\"https://ssl.gongyi.qq.com/cgi-bin/gywcom_qry_donate_dynamic\"},"
			+ "{\"classname\":\"org.executor.testcase.HttpPost\",\"url\":\"https://ssl.gongyi.qq.com/cgi-bin/gywcom_qry_donate_dynamic\"},"
			+ "{\"classname\":\"org.executor.testcase.HttpGet\",\"url\":\"https://ssl.gongyi.qq.com/cgi-bin/gywcom_qry_donate_dynamic\"},"
			+ "{\"classname\":\"org.executor.testcase.HttpGet\",\"url\":\"https://ssl.gongyi.qq.com/cgi-bin/gywcom_qry_donate_dynamic\"}]}";

//	 上报的执行机器名
	List<String> hostnamelist = Arrays.asList("p_zhilingq-PC1", "VM-236-13-centos");

	List<Object> caselist = JSON.parseObject(test_cases).getJSONArray("cases");

	@Autowired
	JedisPool jedisPool;

	/*
	 * cases轮询，case1-k在a机执行，casek-j在b机执行，以此类推
	 */
	@RequestMapping(value = "rolling", method = RequestMethod.GET)
	public String startRolling(@RequestParam(name = "num") int n) {

		if (n <= 0) {

			System.out.println("输入n值无效");

		}

		int start = 0;

		int end = start + n;

		int count = 0;

		while (start < caselist.size()) {

			if (end > caselist.size()) {

				end = caselist.size();

			}

			Map<String, List<Object>> map = new HashMap<String, List<Object>>();

			List<Object> testcases = caselist.subList(start, end);

			map.put(hostnamelist.get(count % hostnamelist.size()), testcases);

			String json = JSON.toJSONString(map);

			startPub(json);

			start = end;

			end = start + n;

			count++;

		}

		return "testdemo";

	}

	/*
	 * cases随机执行，通过设置多用例的个数，分片随机选择a,b执行， case1,2随机选择a,b执行，case3,4随机选择a,b执行，以此类推
	 */
	@RequestMapping(value = "random", method = RequestMethod.GET)
	public String startRandom(@RequestParam(name = "num") int n) throws NoSuchAlgorithmException {

		if (n <= 0) {

			System.out.println("输入n值无效");

		}

		int start = 0;

		int end = start + n;

		while (start < caselist.size()) {

			if (end > caselist.size()) {

				end = caselist.size();

			}

			Map<String, List<Object>> map = new HashMap<String, List<Object>>();

			List<Object> testcases = caselist.subList(start, end);

			map.put(hostnamelist.get(getRandomNum(hostnamelist.size())), testcases);

			String json = JSON.toJSONString(map);

			System.out.println(json);

			startPub(json);

			start = end;

			end = start + n;

		}

		return "testdemo";

	}

	@RequestMapping(value = "test")
	public String test() {

		return "testdemo";

	}

	/*
	 * Redis发布
	 */
	public void startPub(String msg) {

		Jedis publishJedis = jedisPool.getResource();

		publishJedis.publish(channel, msg);

		publishJedis.close();

	}

	/*
	 * n以内的随机数，可以重复
	 */
	public int getRandomNum(int n) throws NoSuchAlgorithmException {

		if (n <= 0) {

			System.out.println("输入n值无效");

		}

		random = SecureRandom.getInstanceStrong();

		return random.nextInt(n);

	}

}
