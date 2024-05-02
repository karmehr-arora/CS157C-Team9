package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Post;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.model.UserWeight;
import edu.team9.fitconnect.repository.PostRepository;
import edu.team9.fitconnect.repository.UserRepository;
import edu.team9.fitconnect.repository.WeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getPostsByUser(String email){
        return postRepository.findPostsByUserId(email);
    }

    public void newPost(String email, String fileName, byte[] fileBytes, String contentType, String title, String body, String category) throws Exception{
        // make sure user exists first
        Optional<User> fetchedUser = userRepository.findByEmail(email);

        if(fetchedUser.isPresent()) {
            Post newPost = new Post(UUID.randomUUID(), fileName, ByteBuffer.wrap(fileBytes), contentType, LocalDateTime.now(), email, title, body, category);
            postRepository.save(newPost);
        }else{
            throw new Exception("user not found");
        }
    }

    public void deletePost(String email, UUID postId) throws Exception{
        Optional<User> fetchedUser = userRepository.findByEmail(email);
        if(fetchedUser.isPresent()) {
            // get post
            Optional<Post> post = postRepository.findById(postId);
            if(post.isPresent()){
                if(email.equals(post.get().getUserId())){
                    postRepository.deleteById(postId);
                }else{
                    throw new Exception("username does not match with post author");
                }
            }else{
                throw new Exception("post not found");
            }
        }else{
            throw new Exception("user not found");
        }
    }

    public List<Post> getPostsByCategory(String category){
        return postRepository.findPostsByCategory(category);
    }
}
