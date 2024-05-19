package edu.team9.fitconnect.Controller;

import edu.team9.fitconnect.model.Category;
import edu.team9.fitconnect.model.Photo;
import edu.team9.fitconnect.repository.CategoryRepository;
import edu.team9.fitconnect.service.CategoryService;
import edu.team9.fitconnect.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.ui.Model;


import static edu.team9.fitconnect.Config.AllowedFileTypes.isImageFile;

@Controller
@AllArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/newUpload")
    public ResponseEntity<String> uploadCategoryPhoto(@RequestParam("Category") String name, @RequestParam("file") MultipartFile file, Principal principal) {
        try {
            //Find user signed in
            if (principal != null) {
                try {
                    if (isImageFile(file.getContentType())) {
                        if(categoryRepository.findCategoryByCategory(name).isEmpty()) {
                            categoryService.insertCategory(name, file.getOriginalFilename(), file.getBytes(), file.getContentType());
                            return ResponseEntity.ok("File uploaded successfully.");
                        } else{
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category Already Exists.");
                        }
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

    @PostMapping(value = "/search")
    public String searchCategories(@RequestParam("find") String find, Model model){
        boolean categoryNotFound = false;
        List<Category> category= categoryService.searchCategory(find);
        if(!category.isEmpty()) {
            model.addAttribute("search", categoryNotFound);
            return "/category-feed/" + category.get(0).getCategory();
        }
        else {
            categoryNotFound = true;
            model.addAttribute("search", categoryNotFound);
            return "main/ConnectHome";
        }
    }

    @GetMapping("/icon")
    public ResponseEntity<?> getPhotoByCategory(String category){
        try{
            //category
            if(category != null){
                List<Category> cat = categoryService.searchCategory(category);
                return ResponseEntity.ok(category);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to see photos.");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must be signed in to see photos.");
        }
    }


    @GetMapping("/icon/{category}")
    public ResponseEntity<?> getFile(@PathVariable("category") String cat){
        try{
            Category category = categoryService.searchCategory(cat).get(0);
            ByteBuffer imageData = category.getFileData();

            // Convert ByteBuffer to byte array
            byte[] bytes = new byte[imageData.remaining()];
            imageData.get(bytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(category.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + category.getFileName() + "\"")
                    .body(bytes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
