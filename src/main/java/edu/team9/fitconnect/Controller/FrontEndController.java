package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Post;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.service.CategoryService;
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
    CategoryService categoryService;

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

    @GetMapping("/files/upload")
    public String uploadFileScreen(){
        return "main/uploadform";
    }

    @PostMapping(value = "/newaccount")
    public String createAccount(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String displayName, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam int weight, @RequestParam int heightInInches, Model model){
        boolean pass = false;
        boolean mail = false;
        if(!userService.checkUser(email)) {
            if (password.equals(confirmPassword)) {
                userService.createUser(email, LocalDateTime.now(), displayName, null, firstName, weight, heightInInches, lastName, password, null, null, User.Role.USER, weight);
                return "main/login";
            }
            else{
                pass = true;
                model.addAttribute("password", pass);
            }
        }
        else{
            mail = true;
            model.addAttribute("email", mail);
        }
        return "main/signup";
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
                return "main/Login";
            }
        }
        return "main/Account";
    }

    @PostMapping(value = "/delete")
    public String deleteAccount(@RequestParam String email, @RequestParam String userPassword, Principal principal){
        User user = (User) userService.loadUserByUsername(principal.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(email.equals(user.getEmail())) {
            if (encoder.matches(userPassword, user.getPassword())) {
                userService.deleteAccount(user);
                return "main/Login";
            }
        }
        return "main/Account";

    }

    @GetMapping("/login")
    public String getLogin() {
        return "main/Login";
    }

    @GetMapping("/connect")
    public String getConnectHome(Model model) throws Exception {
        model.addAttribute("categories", categoryService.findAllCategories());
        return "main/ConnectHome";
    }

    @GetMapping("/connect/new-post")
    public String getPostMaker(Model model) {
        model.addAttribute("title", "New Post");
        model.addAttribute("isEdit", false);
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
                    model.addAttribute("isEdit", true);
                    model.addAttribute("title", "Edit Post");
                    model.addAttribute("post", post);
                    return "main/EditPost";
                }else{
                    //invalid user
                }
            }catch (Exception e){
                //post not found
            }
        }catch(Exception e){
            //for invalid id
        }


        return "main/EditPost";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "main/signup";
    }

    @GetMapping("/")
    public String getHome() {
        return "main/Home";
    }

    @GetMapping("/connect/category-feed/{category}")
    public String getCategoryFeed(@PathVariable("category") String category, Model model) {
//        if(categoryService.searchCategory(category).isEmpty()){
//            return "main/ConnectHome";
//        }
        model.addAttribute("categoryName", category);
        return "main/CategoryFeed";
    }

    @GetMapping("/create-user")
    public String getHome(Model model){
        userService.createUser("costik@costik.com", LocalDateTime.now(), "Constantine", null, "Costi", 170, 67, "khamis", "1234", null, null, User.Role.USER, 160.0);
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

    @GetMapping("/workouts")
    public String getWorkouts() {
        return "main/Workout";
    }
}
