package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Like;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.model.UserWeight;
import edu.team9.fitconnect.repository.LikeRepository;
import edu.team9.fitconnect.repository.UserRepository;
import edu.team9.fitconnect.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public void toggleLike(UUID post, String email){
        // Check if post exists
        Optional<Like> opLike = likeRepository.findLikeByPostIdAndUserEmail(post, email);
        if(opLike.isPresent()){
            // Like exists, delete like
            likeRepository.deleteById(opLike.get().getLikeId());
        }else{
            // Create like on post
            likeRepository.save(new Like(UUID.randomUUID(), email, post));
        }
    }

    public boolean getLike(UUID id, String email){
        Optional<Like> like = likeRepository.findLikeByPostIdAndUserEmail(id, email);
        return (like.isPresent());
    }

    public List<Like> getLikes(UUID post){
        return likeRepository.findLikesByPostId(post);
    }
}
