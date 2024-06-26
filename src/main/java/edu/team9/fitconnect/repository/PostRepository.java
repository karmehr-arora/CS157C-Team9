package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Photo;
import edu.team9.fitconnect.model.Post;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends CassandraRepository<Post, UUID> {
    @AllowFiltering
    List<Post> findPostsByUserId(String userId);

    @AllowFiltering
    List<Post> findPostsByCategory(String category);
}
