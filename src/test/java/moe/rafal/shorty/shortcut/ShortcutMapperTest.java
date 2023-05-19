package moe.rafal.shorty.shortcut;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.ShortcutDto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ShortcutMapperTest {

	private final ShortcutMapper shortcutMapper;

	ShortcutMapperTest() {
		shortcutMapper = Mappers.getMapper(ShortcutMapper.class);
	}

	@Test
	void intoShouldConvertFlawlessly() {
		Shortcut shortcut = ShortcutTestData.getShortcut();
		ShortcutDto shortcutDto = shortcutMapper.into(shortcut);

		ShortcutAssertions.assertSimilarityBetween(shortcut, shortcutDto);
	}

	@Test
	void intoShouldHandleNullGracefully() {
		Assertions.assertDoesNotThrow(() -> shortcutMapper.into(null));
	}
}
