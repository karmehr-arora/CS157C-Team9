package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.DataTransferObject.UserPostDTO;
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

    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            return new User("NULL", "NULL", "NULL", "Not Signed In", 0, 0, "", User.Role.USER, false, true);
        }
        String username = principal.getName();
        return (User) userService.loadUserByUsername(username);
    }

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

    @PostMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestBody String id, Principal principal){
        try{
            String truncatedId = id.substring(1, id.length()-1);
            postService.deletePost(UUID.fromString(truncatedId), getCurrentUser(principal));
            return ResponseEntity.ok("Post deleted");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPost(@PathVariable("id") String id){
        try{
            return ResponseEntity.ok(postService.getPostById(UUID.fromString(id)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<UserPostDTO>> getPostsByCategory(@PathVariable("category") String category){
        return ResponseEntity.ok(postService.getPostsByCategory(category));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable("username") String username){
        return ResponseEntity.ok(postService.getPostsByUser(username));
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<?> getPFP(@PathVariable("id") String id){
        try{
            Post post = postService.getPostById(UUID.fromString(id));

            ByteBuffer imageData = post.getFileData();

            // Convert ByteBuffer to byte array
            byte[] bytes = new byte[imageData.remaining()];
            imageData.get(bytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(post.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + post.getFileName() + "\"")
                    .body(bytes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
