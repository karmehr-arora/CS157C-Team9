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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static edu.team9.fitconnect.Config.AllowedFileTypes.isImageFile;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getPostsByUser(String email){
        return postRepository.findPostsByUserId(email);
    }

    public void savePost(String id, String email, String fileName, byte[] fileBytes, String contentType, String title, String body, String category) throws Exception{
        // make sure user exists first
        Optional<User> fetchedUser = userRepository.findByEmail(email);
        if(fetchedUser.isEmpty()){
            throw new Exception("User not found");
        }

        // Check if id is valid
        try{
            // Post should exist. Retrieve from database
            UUID postId = UUID.fromString(id);

            // Check if post exists
            Optional<Post> opPost = postRepository.findById(postId);
            if(opPost.isEmpty()){
                if(isImageFile(contentType)){
                    // Post does not exist. Create one with the specified user
                    Post newPost = new Post(UUID.randomUUID(), fileName, ByteBuffer.wrap(fileBytes), contentType, LocalDateTime.now(), email, title, body, category);
                    postRepository.save(newPost);
                }else{
                    throw new Exception("Image file required");
                }
            }

            Post post = opPost.get();
            // Post exists. Check for author
            if(!fetchedUser.get().getEmail().equals(post.getUserId())){
                throw new Exception("Log into correct account to edit post");
            }

            if(!body.isEmpty()){
                post.setBodyText(body);
            }

            if(!category.isEmpty()){
                post.setCategory(category);
            }

            if(fileBytes!=null && isImageFile(contentType)){
                post.setFileType(contentType);
                post.setFileName(fileName);
                post.setFileData(ByteBuffer.wrap(fileBytes));
            }

            if(!title.isEmpty()){
                post.setTitleText(title);
            }

            postRepository.save(post);
        }catch (Exception e){
            if(isImageFile(contentType)){
                // Post does not exist. Create one with the specified user
                Post newPost = new Post(UUID.randomUUID(), fileName, ByteBuffer.wrap(fileBytes), contentType, LocalDateTime.now(), email, title, body, category);
                postRepository.save(newPost);
            }else{
                throw new Exception("Image file required");
            }
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

    public Post getPostById(UUID id) throws Exception{
        return postRepository.findById(id).orElseThrow();
    }
    public List<Post> getPostsByCategory(String category){
        return postRepository.findPostsByCategory(category);
    }
}
