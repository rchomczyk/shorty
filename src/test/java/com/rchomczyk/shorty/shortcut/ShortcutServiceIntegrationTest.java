package com.rchomczyk.shorty.shortcut;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openapitools.model.ShortcutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static com.rchomczyk.shorty.shortcut.ShortcutTestData.SHORTCUT_SOURCE;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.SHORTCUT_SOURCE_EMPTY;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.SHORTCUT_SOURCE_ORIGIN;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.getShortcutDto;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
class ShortcutServiceIntegrationTest {

	private final ShortcutService shortcutService;
	private int currentIndex;
	private int futureIndex;

	@Autowired
	ShortcutServiceIntegrationTest(ShortcutRepository shortcutRepository) {
		shortcutService = new ShortcutService(shortcutRepository);
	}

	@BeforeAll
	public void prepareShortcut() {
		currentIndex = shortcutService.createShortcut(getShortcutDto()).getId();
		futureIndex = currentIndex + 1;
	}

	@Test
	void createShortcut() {
		ShortcutDto shortcutDto = getShortcutDto();
		shortcutDto.setSource(SHORTCUT_SOURCE_ORIGIN);

		assertThat(shortcutService.createShortcut(shortcutDto))
			.extracting(Shortcut::getId)
			.isNotNull();
	}

	@Test
	void getShortcutById() {
		assertThat(shortcutService.getShortcut(currentIndex))
			.isPresent();
	}

	@Test
	void getShortcutByIdWithNonExistingShortcut() {
		assertThat(shortcutService.getShortcut(futureIndex))
			.isEmpty();
	}

	@Test
	void getShortcutBySource() {
		assertThat(shortcutService.getShortcutBySource(SHORTCUT_SOURCE))
			.isPresent();
	}

	@Test
	void getShortcutBySourceWithNonExistingShortcut() {
		assertThat(shortcutService.getShortcutBySource(SHORTCUT_SOURCE_EMPTY))
			.isEmpty();
	}

	@Test
	void getShortcutsByIds() {
		assertThat(shortcutService.getShortcutsByIds(Collections.singletonList(currentIndex)))
			.isNotEmpty();
	}
}
