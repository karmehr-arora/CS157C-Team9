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

    @GetMapping("/{name}")
    public String getHome(@PathVariable String name, Model model){
        userService.createUser("Costi", "asdfasf", 0, 0);
        model.addAttribute("name", name);
        return "Home";
    }
}
