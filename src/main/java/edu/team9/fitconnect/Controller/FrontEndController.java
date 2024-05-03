package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Post;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.service.PostService;
import edu.team9.fitconnect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class FrontEndController {
    UserService userService;
    PostService postService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            return new User("NULL", "NULL", "NULL", "Not Signed In", 0, 0, "", User.Role.USER, false, true);
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

    @PostMapping(value = "/updatepassword")
    public String updatePassword(@RequestParam String password, @RequestParam String newPassword, @RequestParam String confirmNewPassword, Principal principal){
        User user = (User) userService.loadUserByUsername(principal.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(password, user.getPassword())){
            if(newPassword.equals(confirmNewPassword)){
                userService.updatePassword(encoder.encode(newPassword), principal.getName());
                return "main/login";
            }
        }
        return "/account";
    }

//    @PostMapping(value = "/delete")
//    public void deleteAccount(@RequestParam String userID, @RequestParam String userPassword){
//        if (userPassword.equals(user.getPassword())){
//            user.delete();
//        }
//    }

    @GetMapping("/login")
    public String getLogin() {
        return "main/Login";
    }

    @GetMapping("/connect")
    public String getConnectHome() {
        return "main/ConnectHome";
    }

    @GetMapping("/connect/new-post")
    public String getPostMaker(Model model) {
        model.addAttribute("post", new Post(null, "", null, "", LocalDateTime.now(), "", "", "", ""));
        return "main/EditPost";
    }

    @GetMapping("/connect/edit-post/{id}")
    public String getPostMaker(@PathVariable("id") String id, Principal principal, Model model) {
        try{
            // Check if ID is a valid UUID
            UUID postId = UUID.fromString(id);

            try{
                // Get post if exists
                Post post = postService.getPostById(postId);

                if(getCurrentUser(principal).getEmail().equals(post.getUserId())){
                    model.addAttribute("Title", "Edit Post");
                    model.addAttribute("post", post);
                    return "main/EditPost";
                }else{
                    //todo: error for invalid user
                }
            }catch (Exception e){
                //todo: error for post not found
            }
        }catch(Exception e){
            //todo: error for invalid id
        }


        return "main/EditPost";
    }

    @GetMapping("/")
    public String getHome() {
        return "main/Home";
    }

    @GetMapping("/create-user")
    public String getHome(Model model){
        userService.createUser("Costi", "Khamis","costik", "costik@costik.com", "1234", 0, 0, User.Role.USER);
        model.addAttribute("name", "test");
        return "main/Home";
    }

    @GetMapping("/myprogress")
    public String getMyProgress() {
        return "main/MyProgress";
    }

    @GetMapping("/account")
    public String getAccount() {
        return "main/Account";
    }
}
