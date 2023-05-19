package moe.rafal.shorty.shortcut;

import org.assertj.core.api.Assertions;

final class ShortcutAssertions {

	private static final String INDEX_FIELD_NAME = "id";

	private ShortcutAssertions() {

	}

	static <X, Y> void assertSimilarityBetween(X expectedValue, Y comparedValue) {
		Assertions.assertThat(comparedValue)
			.usingRecursiveComparison()
			.ignoringFields(INDEX_FIELD_NAME)
			.ignoringCollectionOrder()
			.isEqualTo(expectedValue);
	}
}
