package com.rchomczyk.shorty.shortcut;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.stubbing.Answer;
import org.openapitools.model.ShortcutDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.rchomczyk.shorty.shortcut.ShortcutAssertions.assertSimilarityBetween;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.SHORTCUT_INDEX;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.SHORTCUT_SOURCE;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.getShortcut;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.getShortcutDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShortcutControllerTest {

	private final ShortcutService shortcutService;
	private final ShortcutController shortcutController;

	ShortcutControllerTest() {
		shortcutService = mock(ShortcutService.class);
		shortcutController = new ShortcutController(shortcutService, Mappers.getMapper(ShortcutMapper.class));
	}

	@Test
	void createShortcut() {
		when(shortcutService.createShortcut(any(ShortcutDto.class)))
			.thenAnswer((Answer<Shortcut>) invocationOnMock -> {
				ShortcutDto shortcutDto = invocationOnMock.getArgument(0, ShortcutDto.class);
				Shortcut shortcut = new Shortcut();
				shortcut.setId(SHORTCUT_INDEX);
				shortcut.setSource(shortcutDto.getSource());
				shortcut.setTarget(shortcutDto.getTarget());
				return shortcut;
			});

		ResponseEntity<ShortcutDto> response = shortcutController.createShortcut(getShortcutDto());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertSimilarityBetween(getShortcutDto(), response.getBody());
	}

	@Test
	void findShortcutById() {
		when(shortcutService.getShortcut(any(Integer.class)))
			.thenReturn(Optional.of(getShortcut()));

		ResponseEntity<ShortcutDto> response = shortcutController.findShortcutById(SHORTCUT_INDEX);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertSimilarityBetween(getShortcutDto(), response.getBody());
	}

	@Test
	void findShortcutByIdWithNonExistingShortcut() {
		when(shortcutService.getShortcut(any(Integer.class)))
			.thenReturn(Optional.empty());

		ResponseEntity<ShortcutDto> response = shortcutController.findShortcutById(SHORTCUT_INDEX);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void getShortcutsByIds() {
		when(shortcutService.getShortcutsByIds(any()))
			.thenReturn(Collections.singletonList(getShortcut()));

		ResponseEntity<List<ShortcutDto>> response = shortcutController.findShortcutsByIds(Collections.singletonList(SHORTCUT_INDEX));
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody()).isNotEmpty();
	}

	@Test
	void followShortcut() {
		when(shortcutService.getShortcutBySource(any(String.class)))
			.thenReturn(Optional.of(getShortcut()));

		ResponseEntity<Void> response = shortcutController.followShortcut(SHORTCUT_SOURCE);
		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void followShortcutWithNonExistingShortcut() {
		when(shortcutService.getShortcutBySource(any(String.class)))
			.thenReturn(Optional.empty());

		ResponseEntity<Void> response = shortcutController.followShortcut(SHORTCUT_SOURCE);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}
}
