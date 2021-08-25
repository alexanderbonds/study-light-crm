package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lightcrm.entities.FileInfo;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    @Query("SELECT p.photo FROM Profile p WHERE p.user.login = ?1")
    Optional<FileInfo> findPhotoFileInfoByUserLogin(@NotNull String login);

    @Query("SELECT p.preview FROM Profile p WHERE p.user.login = ?1")
    Optional<FileInfo> findPreviewFileInfoByUserLogin(@NotNull String login);

    @Query("SELECT p.preview FROM Profile p WHERE p.id = ?1")
    Optional<FileInfo> findPreviewFileInfoByProfileId(@NotNull Long id);
}
