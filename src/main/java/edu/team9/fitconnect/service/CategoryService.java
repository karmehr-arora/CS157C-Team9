package edu.team9.fitconnect.service;

import edu.team9.fitconnect.model.Category;
import edu.team9.fitconnect.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;

public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    public void insertCategory(String ctg, String fileName, byte[] data, String type){
        System.out.println("CategoryService");
        Category category = new Category(ctg, LocalDateTime.now(), fileName, ByteBuffer.wrap(data), type);
        categoryRepository.save(category);
    }
}
