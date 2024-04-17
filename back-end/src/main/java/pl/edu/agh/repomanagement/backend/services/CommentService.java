package pl.edu.agh.repomanagement.backend.services;

import pl.edu.agh.repomanagement.backend.models.Comment;

import java.util.Optional;

public interface CommentService {
    Optional<Comment> getCommentByPRUrl(String prUrl);
}
