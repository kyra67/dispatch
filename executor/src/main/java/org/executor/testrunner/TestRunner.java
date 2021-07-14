package org.executor.testrunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.concurrent.Callable;

import com.alibaba.fastjson.JSON;

public class TestRunner implements Callable<Object> {

	public String param;

	public TestRunner(String param) {

		this.param = param;

	}

	@Override
	public Object call() {

		try {

			Class<?> clazz = Class.forName(JSON.parseObject(param).getString("classname"));

			Object obj = clazz.newInstance();

			Method[] methods = clazz.getDeclaredMethods();

//			Thread.sleep(30000);

			for (Method method : methods) {

				System.out.println(method + ", " + method.invoke(obj, JSON.parseObject(param).getString("url")));

			}

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (InstantiationException e) {

			e.printStackTrace();

		} catch (IllegalAccessException e) {

			e.printStackTrace();

		} catch (SecurityException e) {

			e.printStackTrace();

		} catch (IllegalArgumentException e) {

			e.printStackTrace();

		} catch (InvocationTargetException e) {

			e.printStackTrace();

		} 
//		catch (InterruptedException e) {
//
//			e.printStackTrace();
//
//		}

		return null;

	}

	public void getStackTrace() {

		StackTraceElement[] st = Thread.currentThread().getStackTrace();

		if (st == null) {

			System.out.println("no stack...");

		}

		StringBuffer sbf = new StringBuffer();

		for (StackTraceElement stackTraceElement : st) {

			if (sbf.length() > 0) {

				sbf.append("<-");

				sbf.append(System.getProperty("line.separator"));

			}

			sbf.append(MessageFormat.format("{0}.{1}() {2}", stackTraceElement.getClassName(),
					stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));

		}

		System.out.println(sbf.toString());

	}

}
