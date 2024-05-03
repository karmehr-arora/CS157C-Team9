package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Workout;
import edu.team9.fitconnect.service.UserService;
import edu.team9.fitconnect.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<String> createWorkout(@RequestParam("nameOfWorkout") String nameOfWorkout,
                                                @RequestParam("sets") int sets,
                                                @RequestParam("reps") int reps,
                                                @RequestParam("weight") double weight,
                                                Principal principal) {
        try{
            //workoutService.createWorkout(principal.getName(),nameOfWorkout, sets, reps, weight);
            return ResponseEntity.ok("Workout created successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/workouts")
    public String getWorkouts(Model model){
        List<Workout> workouts = workoutService.getAllWorkouts();
        model.addAttribute("workouts", workouts);
        // Logging the contents of the list
        return "main/workout";
    }

}
