package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.model.UserWeight;
import edu.team9.fitconnect.repository.UserRepository;
import edu.team9.fitconnect.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WeightService {

    @Autowired
    private WeightRepository weightRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UserWeight> getWeightsByUser(String email){
        return weightRepository.findUserWeightByEmail(email);
    }

    public void saveWeight(String email, double w) throws Exception{
        // make sure user exists first
        Optional<User> fetchedUser = userRepository.findByEmail(email);

        if(fetchedUser.isPresent()) {
            UserWeight weight = new UserWeight(UUID.randomUUID(), email, w, LocalDateTime.now());
            weightRepository.save(weight);
        }else{
            throw new Exception("user not found");
        }
    }
}
