package org.executor;

import org.executor.pubsub.Subscriber;
import org.junit.jupiter.api.Test;

public class AppTest {
	@Test
	public void contextLoads() {

		new Subscriber().onSubscribe("mytest", 1);

	}
}
