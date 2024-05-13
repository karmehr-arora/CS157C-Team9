package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Comment;
import edu.team9.fitconnect.model.Like;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends CassandraRepository<Comment, UUID> {
    @AllowFiltering
    Optional<Comment> findCommentByPostIdAndUserEmail(UUID postId, String userId);
    @AllowFiltering
    List<Comment> findLikesByPostId(UUID postId);
}
