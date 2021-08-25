package ru.lightcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lightcrm.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
