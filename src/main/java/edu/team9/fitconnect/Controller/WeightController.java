package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.UserWeight;
import edu.team9.fitconnect.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/weight")
public class WeightController {
    @Autowired
    private final WeightService weightService;

    public WeightController(WeightService weightService) {
        this.weightService = weightService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserWeight>> all(Principal principal){
        List<UserWeight> weights = weightService.getWeightsByUser(principal.getName());
        return ResponseEntity.ok(weights);
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestParam("weight") double weight, Principal principal){
        try{
            weightService.saveWeight(principal.getName(), weight);
            return ResponseEntity.ok("Weight saved.");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

}
