package com.rchomczyk.shorty.shortcut;

import org.mapstruct.Mapper;
import org.openapitools.model.ShortcutDto;

@Mapper(componentModel = "spring")
public interface ShortcutMapper {

    ShortcutDto into(Shortcut shortcut);
}
