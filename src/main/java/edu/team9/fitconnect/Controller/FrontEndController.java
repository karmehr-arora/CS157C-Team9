package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.repository.UserRepository;
import edu.team9.fitconnect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class FrontEndController {
    UserService userService;
    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            return new User("NULL", "NULL", "Not Signed In", 0, 0, "", User.Role.USER, false, true);
        }
        String username = principal.getName();
        return (User) userService.loadUserByUsername(username);
    }

    @ModelAttribute
    public void addCommonAttributes(Model model, Principal principal) {
        // If user is logged in
        if (principal != null) {
            User user = getCurrentUser(principal);
            model.addAttribute("user", user);
        }

        // User is logged in or out
        model.addAttribute("loggedIn", principal != null);
    }

    @PostMapping(value = "/account")
    public String updateUsername(@RequestParam String displayName, @RequestParam String confirmDisplayName, Principal principal){
        if(displayName.equals(confirmDisplayName)){
            userService.updateDisplayName(displayName, principal.getName());
            return "main/Account";
        }
        return "main/Account";
    }

//    @PostMapping(value = "/main/Account")
//    public void updatePassword(@RequestParam String newPass, @RequestParam String currPassword){
//        if (currPassword.equals(user.getPassword())){
//            user.setPassword(newPass);
//        }
//    }
//
//    @PostMapping(value = "/main/Account")
//    public void deleteAccount(@RequestParam String userID, @RequestParam String userPassword){
//        if (userPassword.equals(user.getPassword())){
//            user.delete();
//        }
//    }

    @GetMapping("/login")
    public String getLogin() {
        return "main/login";
    }

    @GetMapping("/")
    public String getHome() {
        return "main/Home";
    }

    @GetMapping("/create-user")
    public String getHome(Model model){
        userService.createUser("Costi", "costik@costik.com","costik", "1234", 0, 0, User.Role.USER);
        model.addAttribute("name", "test");
        return "main/Home";
    }

    @GetMapping("/myprogress")
    public String getMyProgress() {
        return "main/My_Progress";
    }

    @GetMapping("/account")
    public String getAccount() {
        return "main/Account";
    }
}
