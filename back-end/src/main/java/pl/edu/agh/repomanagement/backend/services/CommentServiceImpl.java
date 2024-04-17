package pl.edu.agh.repomanagement.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.Comment;
import pl.edu.agh.repomanagement.backend.repositories.CommentRepository;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public Optional<Comment> getCommentByPRUrl(String prUrl) {
        return commentRepository.findByPrUrl(prUrl);
    }
}

