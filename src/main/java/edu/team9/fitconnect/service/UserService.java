package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void createUser(String firstName, String username, String email, String password, int weight, int height, User.Role role){
        // Encode password!
        String encodedPass = bCryptPasswordEncoder().encode(password);
        User newUser = new User(username, firstName, email, weight, height, encodedPass, role, true, false);

        userRepository.save(newUser);

        userRepository.findAll()
                .forEach(v -> System.out.println(v.getFirstName()));
    }


    public void updateDisplayName(String displayName, String email){
        User user = (User)loadUserByUsername(email);
        user.setDisplayname(displayName);
        userRepository.save(user);
    }

    public void updatePassword(String password, String email){
        User user = (User)loadUserByUsername(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    public void deleteAccount(String email){
        User user = (User)loadUserByUsername(email);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }else{
            throw new UsernameNotFoundException("Username was not found");
        }
    }

}
