package com.rchomczyk.shorty.shortcut;

import org.openapitools.model.ShortcutDto;

final class ShortcutTestData {

	static final int SHORTCUT_INDEX = 1;
	static final String SHORTCUT_SOURCE = "local";
	static final String SHORTCUT_SOURCE_ORIGIN = "origin";
	static final String SHORTCUT_SOURCE_EMPTY = "";
	static final String SHORTCUT_TARGET = "http://localhost";

	private ShortcutTestData() {

	}

	static Shortcut getShortcut() {
		Shortcut shortcut = new Shortcut();
		shortcut.setId(SHORTCUT_INDEX);
		shortcut.setSource(SHORTCUT_SOURCE);
		shortcut.setTarget(SHORTCUT_TARGET);
		return shortcut;
	}

	static ShortcutDto getShortcutDto() {
		ShortcutDto shortcutDto = new ShortcutDto();
		shortcutDto.setId(SHORTCUT_INDEX);
		shortcutDto.setSource(SHORTCUT_SOURCE);
		shortcutDto.setTarget(SHORTCUT_TARGET);
		return shortcutDto;
	}
}
