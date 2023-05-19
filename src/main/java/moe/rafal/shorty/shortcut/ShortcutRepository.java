package moe.rafal.shorty.shortcut;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortcutRepository extends CrudRepository<Shortcut, Integer> {

    Optional<Shortcut> findBySource(String source);
}
