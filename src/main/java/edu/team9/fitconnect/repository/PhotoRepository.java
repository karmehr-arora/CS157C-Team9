package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Photo;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface PhotoRepository extends CassandraRepository<Photo, UUID> {
}
