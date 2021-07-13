package org.distribute;

import java.security.NoSuchAlgorithmException;

import org.distribute.controller.StartPubController;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class AppTest {
	
	@Test
	public void contextLoads() {
		
		try {
			new StartPubController().getRandomNum(1);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
