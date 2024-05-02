package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Photo;
import edu.team9.fitconnect.model.User;
import edu.team9.fitconnect.service.PhotoService;
import edu.team9.fitconnect.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
@RequestMapping("/files")
public class PhotoController {
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        try{
            //Find user signed in
            if(principal != null){
                String username = principal.getName();
                User signedInUser = (User) userService.loadUserByUsername(username);
                try {
                    if(isImageFile(file.getContentType())){
                        photoService.uploadFile(file.getOriginalFilename(), file.getBytes(), signedInUser, file.getContentType());
                        return ResponseEntity.ok("File uploaded successfully.");
                    }else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid file type.");
                    }
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
                }
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
        }
    }

    @PostMapping("/profilephoto/upload")
    public ResponseEntity<String> uploadPFP(@RequestParam("file") MultipartFile file, Principal principal) {
        try{
            //Find user signed in
            if(principal != null){
                String username = principal.getName();
                User signedInUser = (User) userService.loadUserByUsername(username);
                try {
                    if(isImageFile(file.getContentType())){
                        signedInUser.setProfilePhoto(ByteBuffer.wrap(file.getBytes()), file.getContentType(), file.getOriginalFilename());
                        userService.saveUser(signedInUser);
                        return ResponseEntity.ok("Profile updated successfully.");
                    }else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid file type.");
                    }
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
                }
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getPhotosByUser(Principal principal){
        try{
            //Find user signed in
            if(principal != null){
                String username = principal.getName();

                List<Photo> photos = photoService.getPhotosByUser(username);
                return ResponseEntity.ok(photos);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to see photos.");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to see photos.");
        }
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> getFile(@PathVariable("id") UUID id){
        try{
            Photo photo = photoService.getPhotoById(id);
            ByteBuffer imageData = photo.getFileData();

            // Convert ByteBuffer to byte array
            byte[] bytes = new byte[imageData.remaining()];
            imageData.get(bytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(photo.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + photo.getFileName() + "\"")
                    .body(bytes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @GetMapping("/profilephoto/{email}")
    public ResponseEntity<?> getPFP(@PathVariable("email") String email){
        try{
            User user = (User) userService.loadUserByUsername(email);

            ByteBuffer imageData = user.getPfpData();

            // Convert ByteBuffer to byte array
            byte[] bytes = new byte[imageData.remaining()];
            imageData.get(bytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(user.getPfpFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + user.getFileName() + "\"")
                    .body(bytes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable("id") UUID id){
        try{
            photoService.deletePhotoById(id);
            return ResponseEntity.ok().body("Photo deleted");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
