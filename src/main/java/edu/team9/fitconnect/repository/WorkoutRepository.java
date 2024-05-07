package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Workout;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface WorkoutRepository extends CassandraRepository<Workout, UUID> {

    // Query to find all workouts for a specific user
    @AllowFiltering
    @Query("SELECT * FROM workout WHERE userId = ?0")
    List<Workout> findAllByUserEmail(String userId);

    // Query to find workouts for a specific user within the current day
    @Query("SELECT * FROM workout WHERE userId = ?0 AND date >= ?1 AND date < ?2")
    List<Workout> findByUserEmailAndWorkoutDate(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
