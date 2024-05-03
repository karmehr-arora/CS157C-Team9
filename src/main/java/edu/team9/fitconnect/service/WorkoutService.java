package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.model.Workout;
import edu.team9.fitconnect.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public void createWorkout(String email, String nameOfWorkout, int sets, int reps, double weight) {
        Workout workout = new Workout(UUID.randomUUID(), email, nameOfWorkout, sets, reps, weight, LocalDateTime.now());
        workoutRepository.save(workout);
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(UUID id) {
        return workoutRepository.findById(id).orElseThrow();
    }

    public void updateNameOfWorkout(UUID id, String nameOfWorkout) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setNameOfWorkout(nameOfWorkout);
        workoutRepository.save(workout);
    }

    public void updateSets(UUID id, int sets) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setSets(sets);
        workoutRepository.save(workout);
    }

    public void updateReps(UUID id, int reps) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setReps(reps);
        workoutRepository.save(workout);
    }

    public void updateWeight(UUID id, double weight) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setWeight(weight);
        workoutRepository.save(workout);
    }

    public void deleteWorkout(UUID id) {
        workoutRepository.deleteById(id);
    }
}
