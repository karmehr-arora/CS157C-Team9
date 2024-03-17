package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public void createUser(String firstName, String email, int weight, int height){
        User newUser = new User(UUID.randomUUID(), firstName, email, weight, height);

        userRepository.save(newUser);

        userRepository.findAll()
                .forEach(v -> System.out.println(v.getFirstName()));
    }

}
