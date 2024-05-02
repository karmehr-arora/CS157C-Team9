package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.service.UserService;
import edu.team9.fitconnect.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/create-workout")
    public ResponseEntity<String> createWorkout() {

    }
}
