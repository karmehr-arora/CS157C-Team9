package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Comment;
import edu.team9.fitconnect.model.Like;
import edu.team9.fitconnect.repository.CommentRepository;
import edu.team9.fitconnect.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsForPost(UUID postId){
        return commentRepository.findLikesByPostId(postId);
    }

    public void saveComment(String email, String comment, UUID postId){
        commentRepository.save(new Comment(UUID.randomUUID(), email, comment, postId));
    }

    public void deleteComment(UUID commentId){
        commentRepository.deleteById(commentId);
    }
}
