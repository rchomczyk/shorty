package com.rchomczyk.shorty.shortcut;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Optional;

import static com.rchomczyk.shorty.shortcut.ShortcutAssertions.assertSimilarityBetween;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.SHORTCUT_INDEX;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.SHORTCUT_SOURCE;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.getShortcut;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.getShortcutDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShortcutServiceTest {

	private final ShortcutRepository shortcutRepository;
	private final ShortcutService shortcutService;

	ShortcutServiceTest() {
		shortcutRepository = mock(ShortcutRepository.class);
		shortcutService = new ShortcutService(shortcutRepository);
	}

	@Test
	void createShortcut() {
		when(shortcutRepository.save(any(Shortcut.class)))
			.thenAnswer((Answer<Shortcut>) invocationOnMock -> {
				Shortcut shortcut = invocationOnMock.getArgument(0, Shortcut.class);
				shortcut.setId(SHORTCUT_INDEX);
				return shortcut;
			});

		assertThat(shortcutService.createShortcut(getShortcutDto()))
			.extracting(Shortcut::getId)
			.isEqualTo(SHORTCUT_INDEX);
	}

	@Test
	void getShortcutById() {
		when(shortcutRepository.findById(any(Integer.class)))
			.thenReturn(Optional.of(getShortcut()));

		Shortcut expectedShortcut = getShortcut();
		Shortcut comparedShortcut = shortcutService.getShortcut(SHORTCUT_INDEX).orElse(null);

		assertSimilarityBetween(expectedShortcut, comparedShortcut);
	}

	@Test
	void getShortcutByIdWithNonExistingShortcut() {
		when(shortcutRepository.findById(any(Integer.class)))
			.thenReturn(Optional.empty());

		assertThat(shortcutService.getShortcut(SHORTCUT_INDEX))
			.isEmpty();
	}

	@Test
	void getShortcutBySource() {
		when(shortcutRepository.findBySource(any(String.class)))
			.thenReturn(Optional.of(getShortcut()));

		Shortcut expectedShortcut = getShortcut();
		Shortcut comparedShortcut = shortcutService.getShortcutBySource(SHORTCUT_SOURCE).orElse(null);

		assertSimilarityBetween(expectedShortcut, comparedShortcut);
	}

	@Test
	void getShortcutBySourceWithNonExistingShortcut() {
		when(shortcutRepository.findBySource(any(String.class)))
			.thenReturn(Optional.empty());

		assertThat(shortcutService.getShortcut(SHORTCUT_INDEX))
			.isEmpty();
	}

	@Test
	void getShortcutsByIds() {
		when(shortcutRepository.findAllById(any()))
			.thenReturn(Collections.singletonList(getShortcut()));

		assertThat(shortcutService.getShortcutsByIds(Collections.singletonList(SHORTCUT_INDEX)))
			.isNotEmpty();
	}
}
