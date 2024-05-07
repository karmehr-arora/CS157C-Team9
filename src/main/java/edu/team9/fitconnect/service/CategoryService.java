package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Category;
import edu.team9.fitconnect.repository.CategoryRepository;
import edu.team9.fitconnect.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;

public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostRepository postRepository;

    public void insertCategory(String ctg, String fileName, ByteBuffer data, String type){
        Category category = new Category(ctg, LocalDateTime.now(), fileName, data, type);
        categoryRepository.save(category);
    }

    List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }
}
