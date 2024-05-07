package edu.team9.fitconnect.Controller;


import edu.team9.fitconnect.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;

import static edu.team9.fitconnect.Config.AllowedFileTypes.isImageFile;

@Controller
@AllArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/newUpload")
    public ResponseEntity<String> uploadCategoryPhoto(@RequestParam("Category") String name, @RequestParam("file") MultipartFile file, Principal principal) {
        System.out.println("Category Controller");
        try {
            //Find user signed in
            if (principal != null) {
                try {
                    if (isImageFile(file.getContentType())) {
                        categoryService.insertCategory(name, file.getOriginalFilename(), file.getBytes(), file.getContentType());
                        return ResponseEntity.ok("File uploaded successfully.");
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid file type.");
                    }
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to upload.");
        }
    }
}
