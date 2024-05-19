package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Category;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void createUser(String email, LocalDateTime datejoined, String displayname, String filename, String firstname, double goalweight, int heightininches, String lastname, String password, ByteBuffer pfpdata, String pfpfiletype, User.Role role, double weight){
        // Encode password!
        String encodedPass = bCryptPasswordEncoder().encode(password);
        User newUser = new User(email, firstname, lastname, displayname, weight, heightininches, encodedPass, role, goalweight, true, false, datejoined, pfpdata, pfpfiletype, filename);
        userRepository.save(newUser);
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

    public void deleteAccount(User user){
        userRepository.delete(user);
        //todo: this may end up being more complicated if there are other objects that depend on the user
    }

    public void saveUser(User user){
        userRepository.save(user);
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

    public boolean checkUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return true;
        }else{
            return false;
        }
    }

}
