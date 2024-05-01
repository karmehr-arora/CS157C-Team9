package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.model.UserWeight;
import edu.team9.fitconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;

import static edu.team9.fitconnect.Config.AllowedFileTypes.isImageFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> all(Principal principal){
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/change-goal")
    public ResponseEntity<String> changeGoal(@RequestParam double weight, Principal principal){
        try{
            //Find user signed in
            if(principal != null){
                String username = principal.getName();
                User signedInUser = (User) userService.loadUserByUsername(username);
                signedInUser.setGoalWeight(weight);
                userService.saveUser(signedInUser);
                return ResponseEntity.ok("Weight goal changed");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to change goal weight.");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to change goal weight.");
        }
    }

}
