package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class FrontEndController {
    UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "main/login";
    }

    @GetMapping("/create-user")
    public String getHome(Model model){
        userService.createUser("Costi", "costik", "costik@costik.com", "1234", 0, 0);
        model.addAttribute("name", "test");
        return "main/Home";
    }
}
