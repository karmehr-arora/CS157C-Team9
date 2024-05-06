package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Photo;
import edu.team9.fitconnect.model.Post;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.service.PhotoService;
import edu.team9.fitconnect.service.PostService;
import edu.team9.fitconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static edu.team9.fitconnect.Config.AllowedFileTypes.isImageFile;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> savePost(@RequestParam(name ="file", required = false) MultipartFile file, @RequestParam("title") String title, @RequestParam("body") String body, @RequestParam("category") String category, @RequestParam("id") String id, Principal principal) {
        try{
            //Find user signed in
            if(principal != null){
                String username = principal.getName();
                User signedInUser = (User) userService.loadUserByUsername(username);
                try{
                    if(file==null){
                        postService.savePost(id, signedInUser.getEmail(), null, null, null, title, body, category);
                    }else{
                        postService.savePost(id, signedInUser.getEmail(), file.getName(), file.getBytes(), file.getContentType(), title, body, category);
                    }

                    return ResponseEntity.ok("Post uploaded successfully.");
                }catch (Exception e){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
                }
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable("category") String category){
        return ResponseEntity.ok(postService.getPostsByCategory(category));
    }

    @GetMapping("/category/{username}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable("username") String username){
        return ResponseEntity.ok(postService.getPostsByUser(username));
    }
}
