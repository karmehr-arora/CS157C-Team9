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
            System.out.println(workoutName);
            int currentSet = Integer.parseInt(workout.get("currentSet"));
            System.out.println(currentSet);
            int reps = Integer.parseInt(workout.get("reps"));
            System.out.println(reps);
            double weight = Double.parseDouble(workout.get("weight"));
            System.out.println(weight);
            workoutService.createWorkout(principal.getName(),workoutName, currentSet, reps, weight);
            System.out.println("Before response entity");
            return ResponseEntity.ok("Workout created successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Workout>> getUserWeights(Model model, Principal principal) {
        try {
            String username = principal.getName();
            LocalDate currentDate = LocalDate.now();
            LocalDateTime startOfDay = LocalDateTime.of(currentDate, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(currentDate, LocalTime.MAX);

            // Call the UserService to fetch user workouts
            List<Workout> userWorkouts = workoutService.getAllWorkoutsByUserAndDate(username, startOfDay, endOfDay);
            for (Workout workout : userWorkouts) {
                System.out.println(workout.toString());
            }

            return ResponseEntity.ok(userWorkouts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @GetMapping("/workouts")
//    public String getWorkouts(Model model){
//        List<Workout> workouts = workoutService.getAllWorkouts();
//        model.addAttribute("workouts", workouts);
//        // Logging the contents of the list
//        return "main/workout";
//    }

}
