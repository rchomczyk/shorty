package com.rchomczyk.shorty.shortcut;

import org.openapitools.model.ShortcutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortcutService {

    private final ShortcutRepository shortcutRepository;

    @Autowired
    public ShortcutService(ShortcutRepository shortcutRepository) {
        this.shortcutRepository = shortcutRepository;
    }

    public Optional<Shortcut> getShortcut(int shortcutId) {
        return shortcutRepository.findById(shortcutId);
    }

    public Optional<Shortcut> getShortcutBySource(String shortcutSource) {
        return shortcutRepository.findBySource(shortcutSource);
    }

    public Iterable<Shortcut> getShortcutsByIds(Iterable<Integer> ids) {
        return shortcutRepository.findAllById(ids);
    }

    public Shortcut createShortcut(ShortcutDto shortcutDto) {
        Shortcut shortcut = new Shortcut();
        shortcut.setSource(shortcutDto.getSource());
        shortcut.setTarget(shortcutDto.getTarget());
        return shortcutRepository.save(shortcut);
    }
}
