package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Photo;
import edu.team9.fitconnect.model.UserWeight;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface WeightRepository extends CassandraRepository<UserWeight, UUID> {
    @AllowFiltering
    List<UserWeight> findUserWeightByUser_id(String userId);
}
