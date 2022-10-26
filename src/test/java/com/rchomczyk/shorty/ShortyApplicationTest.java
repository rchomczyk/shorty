package com.rchomczyk.shorty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ShortyApplicationTest {

	private static final int EXPECTED_BEAN_DEFINITION_COUNT = 367;

	@Test
	void validateIfContextLoads(ApplicationContext context) {
		assertNotNull(context);
	}

	@Test
	void validateIfContextLoadsExpectedBeanDefinitionCount(ApplicationContext context) {
		assertEquals(EXPECTED_BEAN_DEFINITION_COUNT, context.getBeanDefinitionCount());
	}
}
