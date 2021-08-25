package ru.lightcrm.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.lightcrm.entities.FileInfo;
import ru.lightcrm.exceptions.ResourceNotFoundException;

import java.io.IOException;

public interface FileService {

    FileInfo upload(MultipartFile resource);

    void uploadPhoto(MultipartFile resource, String photo);

    FileInfo findById(Long id);

    Resource download(String keyName);

    FileInfo savePreview(MultipartFile origPhotoData) throws IOException ;

    FileInfo findPhotoFileInfoByUserLogin(String login);

    FileInfo findPreviewFileInfoByUserLogin(String login);

    FileInfo findPreviewFileInfoByProfileId(Long id);

    void deleteFiles(FileInfo... fileInfos);
}
