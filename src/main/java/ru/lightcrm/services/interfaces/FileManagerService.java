package ru.lightcrm.services.interfaces;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileManagerService {

    void upload(byte[] resource, String fileName) throws IOException;

    Resource download(String fileName) throws IOException;

    long savePreview(byte[] bytes, String previewFileName) throws IOException;

    void deleteFiles(String... fileNames);
}
