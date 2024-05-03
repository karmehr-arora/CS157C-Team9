package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.Workout;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface WorkoutRepository extends CassandraRepository<Workout, UUID> {

    @Query("SELECT nameOfWorkout FROM workout")
    List<String> findAllWorkoutNames();

}
