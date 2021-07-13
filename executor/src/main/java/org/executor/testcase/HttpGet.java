package org.executor.testcase;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGet {

	public String doGet(String requesturl) {

		HttpURLConnection connection = null;

		InputStream is = null;

		BufferedReader br = null;

		String result = null;

		try {

			URL url = new URL(requesturl);

			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			connection.setConnectTimeout(15000);

			connection.setReadTimeout(60000);

			connection.connect();

			if (connection.getResponseCode() == 200) {

				is = connection.getInputStream();

				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

				StringBuffer sbf = new StringBuffer();

				String temp = null;

				while ((temp = br.readLine()) != null) {

					sbf.append(temp);

					sbf.append("\r\n");

				}

				result = sbf.toString();

			}

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (null != br) {

				try {

					br.close();

				} catch (IOException e) {

					e.printStackTrace();
				}

			}

			if (null != is) {

				try {

					is.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

			connection.disconnect();

		}

		return result;

	}

}
