package moe.rafal.shorty.shortcut;

import org.openapitools.api.ShortcutsApi;
import org.openapitools.model.ShortcutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class ShortcutController implements ShortcutsApi {

    private final ShortcutService shortcutService;
    private final ShortcutMapper shortcutMapper;

    @Autowired
    public ShortcutController(ShortcutService shortcutService, ShortcutMapper shortcutMapper) {
        this.shortcutService = shortcutService;
        this.shortcutMapper = shortcutMapper;
    }

    @Override
    public ResponseEntity<ShortcutDto> createShortcut(ShortcutDto shortcutDto) {
        return ResponseEntity.ok(
            shortcutMapper.into(shortcutService.createShortcut(shortcutDto)));
    }

    @Override
    public ResponseEntity<ShortcutDto> findShortcutById(Integer shortcutId) {
        return shortcutService.getShortcut(shortcutId)
            .map(shortcutMapper::into)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<ShortcutDto>> findShortcutsByIds(List<Integer> shortcutIds) {
        return ResponseEntity.ok(
            StreamSupport.stream(shortcutService.getShortcutsByIds(shortcutIds).spliterator(), false)
                .map(shortcutMapper::into)
                .toList());
    }

    @Override
    public ResponseEntity<Void> followShortcut(String shortcutSource) {
        return shortcutService.getShortcutBySource(shortcutSource)
            .map(shortcut -> ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", shortcut.getTarget())
                .<Void>build())
            .orElse(ResponseEntity.notFound().build());
    }
}
