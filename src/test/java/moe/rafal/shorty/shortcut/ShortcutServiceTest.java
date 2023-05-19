package moe.rafal.shorty.shortcut;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.Optional;

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
				shortcut.setId(ShortcutTestData.SHORTCUT_INDEX);
				return shortcut;
			});

		assertThat(shortcutService.createShortcut(ShortcutTestData.getShortcutDto()))
			.extracting(Shortcut::getId)
			.isEqualTo(ShortcutTestData.SHORTCUT_INDEX);
	}

	@Test
	void getShortcutById() {
		when(shortcutRepository.findById(any(Integer.class)))
			.thenReturn(Optional.of(ShortcutTestData.getShortcut()));

		Shortcut expectedShortcut = ShortcutTestData.getShortcut();
		Shortcut comparedShortcut = shortcutService.getShortcut(ShortcutTestData.SHORTCUT_INDEX).orElse(null);

		ShortcutAssertions.assertSimilarityBetween(expectedShortcut, comparedShortcut);
	}

	@Test
	void getShortcutByIdWithNonExistingShortcut() {
		when(shortcutRepository.findById(any(Integer.class)))
			.thenReturn(Optional.empty());

		assertThat(shortcutService.getShortcut(ShortcutTestData.SHORTCUT_INDEX))
			.isEmpty();
	}

	@Test
	void getShortcutBySource() {
		when(shortcutRepository.findBySource(any(String.class)))
			.thenReturn(Optional.of(ShortcutTestData.getShortcut()));

		Shortcut expectedShortcut = ShortcutTestData.getShortcut();
		Shortcut comparedShortcut = shortcutService.getShortcutBySource(ShortcutTestData.SHORTCUT_SOURCE).orElse(null);

		ShortcutAssertions.assertSimilarityBetween(expectedShortcut, comparedShortcut);
	}

	@Test
	void getShortcutBySourceWithNonExistingShortcut() {
		when(shortcutRepository.findBySource(any(String.class)))
			.thenReturn(Optional.empty());

		assertThat(shortcutService.getShortcut(ShortcutTestData.SHORTCUT_INDEX))
			.isEmpty();
	}

	@Test
	void getShortcutsByIds() {
		when(shortcutRepository.findAllById(any()))
			.thenReturn(Collections.singletonList(ShortcutTestData.getShortcut()));

		assertThat(shortcutService.getShortcutsByIds(Collections.singletonList(ShortcutTestData.SHORTCUT_INDEX)))
			.isNotEmpty();
	}
}
