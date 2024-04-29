package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.model.UserWeight;
import edu.team9.fitconnect.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightService {

    @Autowired
    private WeightRepository weightRepository;

    public List<UserWeight> getWeightsByUser(User user){
        String email = user.getEmail();
        return weightRepository.findUserWeightByUser_id(email);
    }
}
