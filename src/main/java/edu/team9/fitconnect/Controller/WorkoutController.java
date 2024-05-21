package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Workout;
import edu.team9.fitconnect.service.UserService;
import edu.team9.fitconnect.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {
    @Autowired
    private final WorkoutService workoutService;

    @Autowired
    private final UserService userService;

    public WorkoutController(WorkoutService workoutService, UserService userService) {
        this.workoutService = workoutService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWorkout(@RequestBody HashMap<String, String> workout, Principal principal ) {
        try{
            String workoutName = workout.get("workoutName");
            int currentSet = Integer.parseInt(workout.get("currentSet"));
            int reps = Integer.parseInt(workout.get("reps"));
            double weight = Double.parseDouble(workout.get("weight"));
            workoutService.createWorkout(principal.getName(),workoutName, currentSet, reps, weight);
            return ResponseEntity.ok("Workout created successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/today")
    public ResponseEntity<List<Workout>> getUserWorkoutsToday(Principal principal) {
        try {
            String username = principal.getName();
            LocalDate currentDate = LocalDate.now();
            LocalDateTime startOfDay = LocalDateTime.of(currentDate, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(currentDate, LocalTime.MAX);

            // Call the WorkoutService to fetch user workouts
            List<Workout> userWorkouts = workoutService.getAllWorkoutsByUserAndDate(username, startOfDay, endOfDay);

            return ResponseEntity.ok(userWorkouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{duration}")
    public ResponseEntity<List<Workout>> getUserWorkoutsByDuration(@PathVariable("duration") String duration, Principal principal) {
        try {
            long durationLong = Long.parseLong(duration);
            String username = principal.getName();
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(durationLong);

            LocalDateTime startOfPeriod = LocalDateTime.of(startDate, LocalTime.MIN);
            LocalDateTime endOfPeriod = LocalDateTime.of(endDate, LocalTime.MAX);

            List<Workout> userWorkouts = workoutService.getAllWorkoutsByUserAndDate(username, startOfPeriod, endOfPeriod);

            return ResponseEntity.ok(userWorkouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
