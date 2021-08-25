package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lightcrm.entities.Tag;
import ru.lightcrm.entities.dtos.TagDto;
import ru.lightcrm.exceptions.ResourceNotFoundException;
import ru.lightcrm.repositories.TagRepository;
import ru.lightcrm.services.interfaces.TagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagDto findByName(String name) {
        Tag tag = tagRepository.findOneByName(name).orElseThrow(() -> new ResourceNotFoundException(String.format("Тег '%s' не найден", name)));
        return new TagDto(tag);
    }

    public List<TagDto> findAllDto() {
        return tagRepository.findAll().stream().map(TagDto::new).collect(Collectors.toList());
    }
}
