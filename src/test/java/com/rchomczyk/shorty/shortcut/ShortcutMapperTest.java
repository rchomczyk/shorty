package com.rchomczyk.shorty.shortcut;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.ShortcutDto;

import static com.rchomczyk.shorty.shortcut.ShortcutAssertions.assertSimilarityBetween;
import static com.rchomczyk.shorty.shortcut.ShortcutTestData.getShortcut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ShortcutMapperTest {

	private final ShortcutMapper shortcutMapper;

	ShortcutMapperTest() {
		shortcutMapper = Mappers.getMapper(ShortcutMapper.class);
	}

	@Test
	void intoShouldConvertFlawlessly() {
		Shortcut shortcut = getShortcut();
		ShortcutDto shortcutDto = shortcutMapper.into(shortcut);

		assertSimilarityBetween(shortcut, shortcutDto);
	}

	@Test
	void intoShouldHandleNullGracefully() {
		assertDoesNotThrow(() -> shortcutMapper.into(null));
	}
}
