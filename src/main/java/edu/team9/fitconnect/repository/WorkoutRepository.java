package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Workout;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutRepository extends CassandraRepository<Workout, UUID> {

    // Method to find all workouts for a specific user
    @AllowFiltering
    List<Workout> findAllByUserId(String userId);

    // Method to find workouts for a specific user within the current day
    @AllowFiltering
    List<Workout> findByUserIdAndDateBetween(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
