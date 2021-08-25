package ru.lightcrm.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lightcrm.controllers.interfaces.TagController;
import ru.lightcrm.entities.dtos.TagDto;
import ru.lightcrm.services.interfaces.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagService tagService;

    @GetMapping
    public List<TagDto> getTagContent() {
        return tagService.findAllDto();
    }
}
