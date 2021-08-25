package ru.lightcrm.services.interfaces;

import ru.lightcrm.entities.dtos.TagDto;

import java.util.List;

public interface TagService {
    TagDto findByName(String name);
    List<TagDto> findAllDto();
}
