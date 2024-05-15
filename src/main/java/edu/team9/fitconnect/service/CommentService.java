package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Comment;
import edu.team9.fitconnect.model.DataTransferObject.CommentDTO;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.repository.CommentRepository;
import edu.team9.fitconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    public List<CommentDTO> getCommentsForPost(UUID postId){
        // perform table merge
        List<CommentDTO> comments = new ArrayList<>();

        commentRepository.findLikesByPostId(postId).forEach(comment -> {
            Optional<User> user = userRepository.findByEmail(comment.getUserEmail());
            user.ifPresent(value -> comments.add(new CommentDTO(comment.getCommentId(), comment.getUserEmail(), value.getFirstName(), value.getLastName(), comment.getComment(), comment.getPostId(), comment.getTimestamp())));
        });

        return comments;
    }

    public Optional<Comment> getCommentById(UUID id){
        return commentRepository.findById(id);
    }
    public void saveComment(String email, String comment, UUID postId){
        commentRepository.save(new Comment(UUID.randomUUID(), email, comment, postId, LocalDateTime.now()));
    }

    public void deleteComment(UUID commentId){
        commentRepository.deleteById(commentId);
    }
}
