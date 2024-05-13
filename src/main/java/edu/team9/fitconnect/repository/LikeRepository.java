package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Like;
import edu.team9.fitconnect.model.UserWeight;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends CassandraRepository<Like, UUID> {
    @AllowFiltering
    Optional<Like> findLikeByPostIdAndUserEmail(UUID postId, String userId);
    @AllowFiltering
    List<Like> findLikesByPostId(UUID postId);
}
