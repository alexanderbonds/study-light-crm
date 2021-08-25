package ru.lightcrm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.lightcrm.controllers.interfaces.FileController;
import ru.lightcrm.entities.FileInfo;
import ru.lightcrm.exceptions.LightCrmError;
import ru.lightcrm.services.interfaces.FileService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final FileService fileService;

    @Override
    public ResponseEntity<?> upload(MultipartFile attachment) {
        fileService.upload(attachment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> download(Long id) {
        FileInfo foundFile = fileService.findById(id);
        Resource resource = fileService.download(foundFile.getKeyName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foundFile.getType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + foundFile.getName())
                .body(resource);
    }

    @Override
    public ResponseEntity<?> uploadPhoto(MultipartFile attachment, Principal principal) {
        fileService.uploadPhoto(attachment, principal.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> downloadPhoto(Principal principal) {
        FileInfo foundFile = fileService.findPhotoFileInfoByUserLogin(principal.getName());
        if (foundFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new LightCrmError(
                            HttpStatus.NOT_FOUND.value(),
                            "Отсутствтует фото пользователя с логином: " + principal.getName()));
        }
        Resource resource = fileService.download(foundFile.getKeyName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foundFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);
    }

    @Override
    public ResponseEntity<?> downloadPreview(Principal principal) {
        FileInfo foundFile = fileService.findPreviewFileInfoByUserLogin(principal.getName());
        if (foundFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new LightCrmError(
                            HttpStatus.NOT_FOUND.value(),
                            "Отсутствтует превью пользователя с логином: " + principal.getName()));
        }
        Resource resource = fileService.download(foundFile.getKeyName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foundFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);
    }

    @Override
    public ResponseEntity<?> downloadPreviewByProfileId(Long id) {
        FileInfo foundFile = fileService.findPreviewFileInfoByProfileId(id);
        if (foundFile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new LightCrmError(
                            HttpStatus.NOT_FOUND.value(),
                            "Отсутствтует превью профиля с id: " + id));
        }
        Resource resource = fileService.download(foundFile.getKeyName());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(foundFile.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .body(resource);
    }
}
