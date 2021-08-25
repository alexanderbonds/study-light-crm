package ru.lightcrm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.lightcrm.services.interfaces.FileManagerService;

import javax.annotation.PostConstruct;
import javax.validation.ValidationException;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileManagerServiceImpl implements FileManagerService {

    @Value("${filemanager.files_location}")
    private String directoryPath;

    @Value("${filemanager.max_preview_size}")
    private int maxPreviewSize;

    @PostConstruct
    public void createPath() throws IOException {
        if (Files.notExists(Paths.get(directoryPath))) {
            Files.createDirectories(Paths.get(directoryPath));
        }
    }

    @Override
    public void upload(byte[] resource, String fileName) throws IOException {
        Path path = Paths.get(directoryPath, fileName);
        Path file = Files.createFile(path);
        try (FileOutputStream fileWriter = new FileOutputStream(file.toFile())) {
            fileWriter.write(resource);
        }
    }

    @Override
    public Resource download(String fileName) throws IOException {
        Path path = Paths.get(directoryPath, fileName);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException();
        }
    }

    @Override
    public void deleteFiles(String... fileNames) {
        deleteFilesFromIndex(0, new ArrayList<>(), fileNames);
    }

    private void deleteFilesFromIndex(int startIndex, ArrayList<String> badFiles, String... fileNames) {
        int index = startIndex;
        try {
            for (; index < fileNames.length; index++) {
                Files.delete(Paths.get(directoryPath, fileNames[index]).toAbsolutePath());
                log.info("Удален файл: {}", fileNames[index]);
            }
            if (!badFiles.isEmpty()) {
                String badFileNames = String.join(", ", badFiles);
                String message = String.format("Невозможно удалить файл(ы): %s", badFileNames);
                log.warn(message);
//            throw new IOException(message);
            }
        } catch (IOException e) {
            badFiles.add(fileNames[index]);
            deleteFilesFromIndex(++index, badFiles, fileNames);
        }
    }

    public long savePreview(byte[] originalResource, String previewFileName) throws IOException {
        Path path = Paths.get(directoryPath, previewFileName);
        try {
            Thumbnails.of(new ByteArrayInputStream(originalResource)).size(maxPreviewSize, maxPreviewSize).toFile(path.toFile());
        } catch (IOException e) {
            throw new ValidationException("Неподдерживаемый формат изображения");
        }
        return Files.size(path);
    }
}
