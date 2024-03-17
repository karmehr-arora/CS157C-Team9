package edu.team9.fitconnect.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FrontEndController {
    @GetMapping("/{name}")
    public String getHome(@PathVariable String name, Model model){
        model.addAttribute("name", name);
        return "Home";
    }
}
